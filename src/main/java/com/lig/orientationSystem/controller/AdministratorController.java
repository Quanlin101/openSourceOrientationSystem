package com.lig.orientationSystem.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lig.orientationSystem.controller.dto.*;
import com.lig.orientationSystem.domain.*;
import com.lig.orientationSystem.service.impl.AdministratorServiceImpl;
import com.lig.orientationSystem.until.token.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@PassToken
@CrossOrigin
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    AdministratorServiceImpl administratorService;

    //数据看板
    //投递数据
    @GetMapping("/total")
    public R showDeliveryData(){
        ArrayList<DeliveryDataDTO> deliveryDataDTOS = administratorService.showDeliveryData();
        return R.ok(deliveryDataDTOS);
    }
    //详情
    @GetMapping("/details")
    public R showDetails(){
        ArrayList<DataDetailsDTO> dataDetailsDTOS = administratorService.showDetails();
        return R.ok(dataDetailsDTOS);
    }
    //信息来源
    @GetMapping("/messageFrom")
    public R showMessageFrom(){
        ArrayList<MessageFromDTO> messageFromDTOS = administratorService.showMessageFrom();
        return R.ok(messageFromDTOS);
    }
    //内推人
    @GetMapping("/pushInMan")
    public R showPushInMan(){
        ArrayList<PushInCountDTO> pushInCountDTOS = administratorService.showPushInMan();
        return R.ok(pushInCountDTOS);
    }

    //简历管理 简历查看，状态查看
    //把所以简历的数据全传过去？还是每次都分页查？ 分页查qwq
    //简历查看
    @PassToken
    @GetMapping("/resume/{current}/{size}")
    public R readResume(@PathVariable int current, @PathVariable int size, @RequestParam String project, @RequestParam String station, @RequestParam int status){
        IPage<Resume> resumeIPage = administratorService.readResume(current, size, project, station, status);
        return R.ok(resumeIPage);
    }

    //面试官管理 增删改面试官信息，是否接收简历， 接收简历数？
    //面试官查看
    //项目、 岗位、状态
    @PassToken
    @GetMapping("/interviewer/{current}/{size}")
    public R readInterviewer(@PathVariable int current, @PathVariable int size, @RequestParam String station, @RequestParam int status){
        IPage interviewer = administratorService.readInterviewer(current, size, station, status);
        return R.ok(interviewer);
    }

    //添加面试官
    @PassToken
    @PostMapping("/add")
    public R addInterviewer(@RequestBody Interviewer interviewer){
        System.out.println(interviewer);
        MethodPassWrapper methodPassWrapper = administratorService.addInterviewer(interviewer);
        if (!methodPassWrapper.isSuccess()){
            return R.error("无效的手机号");
        }
        return R.ok();
    }
    //删除面试官
    @PassToken
    @GetMapping("/delete/{userId}")
    public R deleteInterviewer(@PathVariable String userId){
        MethodPassWrapper passWrapper = administratorService.deleteInterviewer(userId);
        if (!passWrapper.isSuccess()){
            return R.error(passWrapper.getDesc());
        }
        return R.ok();
    }
    //修改面试官信息 （姓名，岗位，微信号）
    @PostMapping("/adChange")
    public R changeInterviewer(@RequestBody Interviewer newInfo){
        administratorService.changeInterviewer(newInfo);
        return R.ok();
    }
    //修改面试官是否能接收简历
    @PostMapping("/receive")
    public R isReceiveResume(@RequestBody JSONObject jsonObject){
        String phoneNumber = jsonObject.getString("phoneNumber");
        Boolean isReceive = jsonObject.getBoolean("isReceive");
        administratorService.isReceiveResume(phoneNumber, isReceive);
        return R.ok();
    }

    //项目管理 添加项目，更新面试官的可移交次数
    @PostMapping("/addProject")
    public R addProject(@RequestParam String projectName){
        Resume.thisTimeProject = projectName;
        administratorService.refreshChange();
        return R.ok();
    }

    //简历表单修改

    //查看岗位数据
    @GetMapping("/station")
    public R readStations(){
        ArrayList<Station> stations = StationList.arrayList;
        return R.ok(stations);
    }

    //岗位上移、下移 code : 0 -> 上移 ， 1 -> 下移
    @PostMapping("/upDownChange")
    public R addDeleteStation(@RequestBody JSONObject jsonObject){
        int code = jsonObject.getInteger("code");
        int number = jsonObject.getInteger("number");
        MethodPassWrapper methodPassWrapper = administratorService.changeStation(code, number);
        if (methodPassWrapper.isSuccess()){
            return R.ok();
        }
        else {
            return R.error(methodPassWrapper.getDesc());
        }
    }
    //岗位内部信息编辑
    @PassToken
    @PostMapping("/changeInner")
    public R changeInner(@RequestBody JSONObject jsonObject){
        String postName = jsonObject.getString("postName");
        String name = jsonObject.getString("name");
        String desc = jsonObject.getString("desc");
        System.out.println(jsonObject);
        administratorService.changeInner(postName, name, desc);
        return R.ok();
    }
    //岗位添加
    @PassToken
    @PostMapping("/addStation")
    public R changeStation(@RequestBody Station station){
        MethodPassWrapper methodPassWrapper = administratorService.addStation(station);
        if (!methodPassWrapper.isSuccess()){
            return R.error(methodPassWrapper.getDesc());
        }
        return R.ok();
    }
}
