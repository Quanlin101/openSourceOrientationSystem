package com.lig.orientationSystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lig.orientationSystem.controller.dto.DataDetailsDTO;
import com.lig.orientationSystem.controller.dto.DeliveryDataDTO;
import com.lig.orientationSystem.controller.dto.MessageFromDTO;
import com.lig.orientationSystem.controller.dto.PushInCountDTO;
import com.lig.orientationSystem.dao.AdministratorMapper;
import com.lig.orientationSystem.domain.*;
import com.lig.orientationSystem.service.AdministratorService;
import com.lig.orientationSystem.until.AccessTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

@Service
@Transactional
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {

    @Autowired
    AdministratorMapper administratorMapper;

    static MethodPassWrapper methodPassWrapper = new MethodPassWrapper<>();

    DecimalFormat df = new DecimalFormat("0.00");
    //数据看板
    //投递数据
    public ArrayList<DeliveryDataDTO> showDeliveryData() {
        ArrayList<DeliveryDataDTO> deliveryDataDTOS = new ArrayList<>(10);
        int total = administratorMapper.selectTotal();

        Station station = new Station();
        for (int i = 0; i < StationList.arrayList.size(); ++i) {
            DeliveryDataDTO deliveryDataDTO = new DeliveryDataDTO();
            station = StationList.arrayList.get(i);
            deliveryDataDTO.setStation(station.getName());
            deliveryDataDTO.setNumber(administratorMapper.showDeliveryData(station.getName()));
            deliveryDataDTO.setPoint(df.format((float) deliveryDataDTO.getNumber() / total));
            deliveryDataDTOS.add(deliveryDataDTO);
        }

        return deliveryDataDTOS;
    }

    //详情
    public ArrayList<DataDetailsDTO> showDetails() {
        ArrayList<DataDetailsDTO> dataDetailsDTOS = new ArrayList<>(10);
        Station station;
        for (int i = 0; i < StationList.arrayList.size(); ++i) {
            DataDetailsDTO detailsDTO = new DataDetailsDTO();
            station = StationList.arrayList.get(i);
            detailsDTO.setStation(station.getName());
            detailsDTO.setPass(administratorMapper.getPass(station.getName()));
            System.out.println(administratorMapper.getPass(station.getName()));
            detailsDTO.setNoPass(administratorMapper.getNoPass(station.getName()));
            detailsDTO.setPoint(df.format( (float)detailsDTO.getPass()/(detailsDTO.getPass()+detailsDTO.getNoPass())));
            dataDetailsDTOS.add(detailsDTO);
            System.out.println(detailsDTO);
        }
        System.out.println(dataDetailsDTOS.toString());
        return dataDetailsDTOS;
    }

    //信息来源
    public ArrayList<MessageFromDTO> showMessageFrom() {
        ArrayList<String> messageFromList = new ArrayList<>();
        messageFromList.add("冬令营/夏令营");
        messageFromList.add("自媒体");
        messageFromList.add("海报");
        messageFromList.add("其他");
        ArrayList<MessageFromDTO> messageFromDTOS = new ArrayList<>();
        int total = administratorMapper.selectTotal();
        for (String messageFrom : messageFromList) {
            MessageFromDTO messageFromDTO = new MessageFromDTO();
            messageFromDTO.setMessageFrom(messageFrom);
            messageFromDTO.setNumber(administratorMapper.selectMessageNumber(messageFrom));
            messageFromDTO.setPoint(df.format((float)messageFromDTO.getNumber()/total));
            messageFromDTOS.add(messageFromDTO);
        }
        System.out.println(messageFromDTOS);
        return messageFromDTOS;
    }
    public ArrayList<PushInCountDTO> showPushInMan() {
        ArrayList<PushInCountDTO> pushInCountDTOS = administratorMapper.selectPushInMan();
        return pushInCountDTOS;
    }

    //简历管理 简历查看，状态查看
    //把所以简历的数据全传过去？还是每次都分页查？
    //简历查看
    public IPage<Resume> readResume(int current, int size, String project, String station, int status) {
        Page<Resume> page = new Page(current, size);
        IPage<Resume> resumeIPage;
        boolean checked;
        boolean interview;
        boolean pass;
        if (status == 0){//全部状态
            resumeIPage = administratorMapper.readResumeAllStatus(page, project, station);
        }else {
            if (status == 1){//未查看
                checked = false;
                interview = false;
                pass = false;
            }else if (status == 2){//未面评
                checked = true;
                interview = false;
                pass = false;
            }else  if (status == 3){//通过
                checked = true;
                interview = true;
                pass = true;
            }
            else {//未通过
                checked = true;
                interview = true;
                pass = false;
            }
            resumeIPage = administratorMapper.readResume(page, project, station, checked, interview, pass);
        }
        return resumeIPage;
    }

    //面试官管理 增删改面试官信息，是否接收简历， 接收简历数？
    //查看面试官  筛选：项目筛选、岗位筛选、是否接收简历筛选(全部)
    public IPage readInterviewer(int current, int size, String station, int status) {
        Page<Interviewer> page = new Page<>(current, size);
        boolean receive;
        IPage<Interviewer> interviewerIPage;
        if (status == 0){//全部
            interviewerIPage = administratorMapper.readInterviewerAllReceive(page, station);
        }
        else {
            if (status == 1){//接收
                receive = true;
            }else {// 2 未面评
                receive = false;
            }
            interviewerIPage = administratorMapper.readInterviewer(page, station, receive);
        }

        return interviewerIPage;
    }

    @Autowired
    RestTemplate restTemplate;

    //添加面试官（获取userid）
    public MethodPassWrapper getUserId(String phoneNumber) {
        String access_token = AccessTokenUtils.access_token;
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserid?access_token=" + access_token;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobile", phoneNumber);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, jsonObject.toString(), String.class);
        String body = responseEntity.getBody();
        JSONObject jsonBody = JSONObject.parseObject(body);
        String userid;
        if ("0".equals(String.valueOf(jsonBody.get("errcode")))) {
            methodPassWrapper.setSuccess(true);
            userid = String.valueOf(jsonBody.get("userid"));
            methodPassWrapper.setData(userid);
        } else {
            methodPassWrapper.setSuccess(false);
        }
        return methodPassWrapper;
    }

    //添加面试官
    public MethodPassWrapper addInterviewer(Interviewer interviewer) {
        methodPassWrapper = getUserId(interviewer.getPhoneNumber());
        if (!methodPassWrapper.isSuccess()) {
            methodPassWrapper.setDesc("获取userid失败");
            return methodPassWrapper;
        }
        System.out.println(String.valueOf(methodPassWrapper.getData()));
        interviewer.setUserId(String.valueOf(methodPassWrapper.getData()));
        System.out.println("STATION:" + interviewer.getStation());
        administratorMapper.addInterviewer(interviewer.getUserId(), interviewer.getPhoneNumber(), interviewer.getName(), interviewer.getStation());
        methodPassWrapper.setSuccess(true);
        return methodPassWrapper;
    }

    //删除面试官
    public MethodPassWrapper deleteInterviewer(String phoneNumber) {
        administratorMapper.deleteInterviewer(phoneNumber);
        Interviewer interviewer = administratorMapper.selectByPhone(phoneNumber);
        if (interviewer != null) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("删除失败");
            return methodPassWrapper;
        }
        methodPassWrapper.setSuccess(true);
        return methodPassWrapper;
    }

    //修改面试官信息 （姓名，岗位，手机号）
    public void changeInterviewer(Interviewer newInfo) {
        administratorMapper.changeInterviewer(newInfo.getPostPhoneNumber(), newInfo.getName(), newInfo.getStation(), newInfo.getPhoneNumber());
    }

    //修改面试官是否能接收简历
    public void isReceiveResume(String phoneNumber, Boolean isReceive) {
        administratorMapper.isReceiveResume(phoneNumber, isReceive);
    }

    //简历表单修改 （好像和我莫得关系0.0）
    //岗位修改
    StationList stationList = new StationList();

    public MethodPassWrapper changeStation(int code, int number) {
        if (code == 0) {
            stationList.up(number);
        } else if (code == 1) {
            stationList.down(number);
        } else {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("code错误，请检查code");
            return methodPassWrapper;
        }
        methodPassWrapper.setSuccess(true);
        System.out.println(stationList.arrayList);
        return methodPassWrapper;
    }

    //编辑岗位
    public MethodPassWrapper changeInner(String postName, String name, String desc) {
        System.out.println(StationList.arrayList);
        Station newStation = new Station(name, desc);
        boolean change = stationList.change(postName, newStation);
        if (!change) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("岗位修改失败");
            return methodPassWrapper;
        }
        System.out.println(StationList.arrayList);
        methodPassWrapper.setSuccess(true);
        return methodPassWrapper;
    }

    //增加岗位
    public MethodPassWrapper addStation(Station station) {
        boolean exist = stationList.exist(station.getName());
        if (exist) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("该岗位已存在:(");
            return methodPassWrapper;
        }
        stationList.arrayList.add(station);
        methodPassWrapper.setSuccess(true);
        return methodPassWrapper;
    }

    //项目更新时更新面试官移交简历次数
    public void refreshChange() {
        administratorMapper.refreshChange();
    }


}
