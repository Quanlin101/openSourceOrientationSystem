package com.lig.orientationSystem.controller;

import com.lig.orientationSystem.controller.dto.R;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.domain.Resume;
import com.lig.orientationSystem.domain.enums.ResumeStatusCodeEnums;
import com.lig.orientationSystem.service.impl.IntervieweeServiceImpl;
import com.lig.orientationSystem.service.impl.OssUtils;
import com.lig.orientationSystem.until.token.PassToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/interviewee")
public class IntervieweeController {
    @Autowired
    IntervieweeServiceImpl intervieweeService;
    @Autowired
    OssUtils fileService;

    //    @PostMapping
//    //@RequestBody Interviewee interviewee, @RequestBody Resume resume,
//    public R submit(@RequestParam(value = "file",required = false) MultipartFile[] multipartFiles) throws IOException {
//        String phoneNumber = "110";
//        boolean submit = intervieweeService.submit(phoneNumber, multipartFiles);
//        if (submit) {
//            return R.ok();
//        }
//        else {
//            return R.error("上传文件失败");
//        }
//    }
    @PassToken
    @GetMapping
    public R search(@Param("phoneNumber") String phoneNumber) {
        Resume queryResume = intervieweeService.queryResume(phoneNumber);
        if (queryResume != null) {
            int status;
            if (!queryResume.isChecked()&&!queryResume.isInterview()){
                status = 1;//等待查看
            }else if (queryResume.isChecked()&&!queryResume.isInterview()){
                status = 2;//等待面试
            }else if (queryResume.isInterview()&&queryResume.isPass()){
                status = 3;//通过
            }else if(queryResume.isInterview()&&!queryResume.isPass()){
                status = 4;//不通过
            }else {
                status = 5;//异常
            }
            return R.ok(status);
        } else {
            return R.error("未查询到简历信息");
        }
    }

//    https://blog.csdn.net/qq_19635589/article/details/80223301
//    文件上传jackson和MultipartFile序列化问题
    @PassToken
    @PostMapping
    public R submit(@RequestPart("json") Resume resume, @RequestPart(value = "file",required = false) MultipartFile practice) {
        //MultiValueMap<String,String> resume
        if (resume == null) {
            return R.error("简历为空");
        }
        resume.setSubmit_time(new Date());

        if (resume.isHasPractice()) {
            if (practice != null){
                String fileURL;
                MethodPassWrapper methodPassWrapper= fileService.upload(practice, resume.getName());
                if (!methodPassWrapper.isSuccess()){
                    return R.error(methodPassWrapper.getDesc());
                }
                fileURL = (String)methodPassWrapper.getData();
                intervieweeService.saveFileURL(resume.getPhoneNumber(),fileURL);
            }
            else {
                return R.error("文件为空,简历未能附带有效文件");
            }
        }
        MethodPassWrapper success = intervieweeService.submit(resume);
        if (!success.isSuccess()){
            return R.error(success.getDesc());
        }
        resume.setFileURL(success.getData().toString());
        return R.ok();
    }
}
