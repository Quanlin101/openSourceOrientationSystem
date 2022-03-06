package com.lig.orientationSystem.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lig.orientationSystem.controller.dto.R;
import com.lig.orientationSystem.domain.Interviewer;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.domain.Resume;
import com.lig.orientationSystem.service.impl.InterviewerServiceImpl;
import com.lig.orientationSystem.until.JWTUtils;
import com.lig.orientationSystem.until.token.PassToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/interviewer")
public class InterviewerController {

    @Autowired
    InterviewerServiceImpl interviewerService;

    //查看刚发过来的简历设置已读
    @GetMapping("/read/{resumeId}")
    public R setRead(@PathVariable String resumeId){

//        System.out.println(resumeId);
        interviewerService.setRead(resumeId);
        return R.ok();
    }
    //面试
    @GetMapping("/check/{resumeId}")
    public R setCheck(@PathVariable String resumeId){
        MethodPassWrapper methodPassWrapper = interviewerService.setCheck(resumeId);
        if (!methodPassWrapper.isSuccess()){
            return R.error(methodPassWrapper.getDesc());
        }
        return R.ok();
    }


    //分页查询简历
    //未读，未提交面评，已处理
    @GetMapping("/{current}/{size}/{status}")
    public R queryTest(HttpServletRequest request, @PathVariable int current, @PathVariable int size, @PathVariable int status){
        String token = request.getHeader("Authorization");
//        System.out.println("token:" + token);
        Map<String, Claim> claimMap = JWTUtils.validateToken(token);
//        if (claimMap == null){
//            R r = new R(false,2,"token过期",null);
//            return r;
//        }
//        System.out.println("claimMap" + claimMap.toString());
        String userId = claimMap.get("UserId").asString();
//        String userId = "Lig..Wen";
        IPage<Resume> resumeIPage = interviewerService.readResume(userId, current, size, status);
        return R.ok(resumeIPage);
    }

    //提交面评
    @PostMapping
    public R saveEvaluation(@RequestBody JSONObject jsonObject){
        int resumeId;
        String evaluation;
        boolean pass;
        try {
            resumeId = jsonObject.getInteger("resumeId");
            evaluation = jsonObject.getString("evaluation");
            pass = jsonObject.getBoolean("pass");
        }catch (NullPointerException e){
            return R.error("json有误:)");
        }
        MethodPassWrapper success = interviewerService.saveEvaluation(resumeId, evaluation, pass);
        if (!success.isSuccess()){
            return R.error(success.getDesc());
        }
        return R.ok();
    }
    //移交面评
    @PostMapping("/change")
    public R changeInterviewer(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        String token = request.getHeader("Authorization");
        System.out.println("token:" + token);
        Map<String, Claim> claimMap = JWTUtils.validateToken(token);
//        System.out.println("claimMap" + claimMap.toString());
        String userId = claimMap.get("UserId").asString();
        Interviewer interviewer = interviewerService.getInterviewerByUserId(userId);
        System.out.println(interviewer.toString());
        if (interviewer == null){
            return R.error("移交失败，UserId有误");
        }
        String postPhoneNumber = interviewer.getPhoneNumber();

        String phoneNumber = null;
        int resumeId = -1;
        try {
            phoneNumber = jsonObject.getString("phoneNumber");
            resumeId = jsonObject.getInteger("resumeId");
        }catch (NullPointerException e){
            return R.error("json有误:)");
        }
//        System.out.println("0.0");
        MethodPassWrapper methodPassWrapper = interviewerService.changeInterviewer(postPhoneNumber, phoneNumber, resumeId);
//        System.out.println("0.0");
        if (!methodPassWrapper.isSuccess()){
            return R.error(methodPassWrapper.getDesc());
        }
        return R.ok();
    }
}
