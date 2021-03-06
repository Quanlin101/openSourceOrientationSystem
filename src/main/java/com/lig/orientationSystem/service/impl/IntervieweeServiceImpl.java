package com.lig.orientationSystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lig.orientationSystem.controller.dto.R;
import com.lig.orientationSystem.dao.IntervieweeMapper;
import com.lig.orientationSystem.domain.Interviewer;
import com.lig.orientationSystem.domain.Resume;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.service.IntervieweeService;
import com.lig.orientationSystem.service.InterviewerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class IntervieweeServiceImpl extends ServiceImpl<IntervieweeMapper, Resume> implements IntervieweeService {

    @Autowired
    IntervieweeMapper intervieweeMapper;

    @Autowired
    InterviewerServiceImpl interviewerService;

    static MethodPassWrapper methodPassWrapper = new MethodPassWrapper<>();

    //    @Transactional
//    public boolean submit(String phoneNumber, MultipartFile[] multipartFiles) throws IOException {
//        for (MultipartFile file : multipartFiles) {
//            if (!file.isEmpty()) {
//                byte[] byteFile = file.getBytes();
//                intervieweeMapper.insertFile(phoneNumber, byteFile);
//            }
//            else return false;
//        }
//        return true;
//    }
//保存简历
    @Transactional(rollbackFor = Exception.class)
    public MethodPassWrapper submit(Resume resume) {

        Resume existedResume = null;
        try {
            existedResume = intervieweeMapper.queryResume(resume.getPhoneNumber(), Resume.thisTimeProject);
        }catch (TooManyResultsException e){
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("查询错误，该手机号已投递太多次简历！");
            log.warn("一个手机号投递简历多次 phoneNumber:{}",resume.getPhoneNumber());
            return methodPassWrapper;
        }

        if (existedResume!=null){
            if (Resume.thisTimeProject.equals(existedResume.getProject())){
                methodPassWrapper.setSuccess(false);
                methodPassWrapper.setDesc("一个项目每个手机号只能提交一次简历呦\n0.0");
                log.warn("一个手机号投递简历多次 phoneNumber:{}",resume.getPhoneNumber());
                return methodPassWrapper;
            }
        }

        int existInterviewer = intervieweeMapper.selectInterviewerByStation(resume.getStation());

//        Interviewer interviewer = intervieweeMapper.getProperInterviewer();

        if (existInterviewer == 0){
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("该岗位暂时没有面试官:(");
            log.info("向没面试官的岗位投简历了，额 station：{}",resume.getStation());
            return methodPassWrapper;
        }

        //数据库受影响的行数
        int insert = intervieweeMapper.insert(resume);
        boolean distributeResume = intervieweeMapper.distributeResume(resume.getResumeId(), resume.getStation());
        //获取分发完成的userId
        String userId = intervieweeMapper.selectUserId(resume.getResumeId());
        //更新面试官简历数量
        boolean updateResumeNumber = intervieweeMapper.updateResumeNumber(resume.getResumeId(),userId);

        //获取分发完简历面试官的user_ id,进行消息推送
        boolean sendMessage = interviewerService.sendMessage(userId, resume);
        interviewerService.addUndone(userId);

        if (insert != 1 && updateResumeNumber) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("简历写入失败");
            log.error("数据库resume表更新建立失败 resume:{}",resume);
            return methodPassWrapper;
        } else if (!distributeResume) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("简历分发失败");
            log.error("数据库interviewer和中间表更新失败 resume:{}",resume);
            return methodPassWrapper;
        } else if (!sendMessage){
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("面试官推送失败");
            log.error("简历的信息没有进行推送 resume:{}",resume);
            return methodPassWrapper;
        }
        methodPassWrapper.setSuccess(true);
        return methodPassWrapper;
    }

    public Resume queryResume(String phoneNumber) {
        Resume queryResume = intervieweeMapper.queryResume(phoneNumber,Resume.thisTimeProject);
        return queryResume;
    }


    //保存文件路径
    public void saveFileURL(String phoneNumber,String fileURL) {
        intervieweeMapper.saveFileURL(phoneNumber,fileURL);
    }
}
