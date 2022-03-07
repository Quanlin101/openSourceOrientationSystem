package com.lig.orientationSystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lig.orientationSystem.controller.dto.R;
import com.lig.orientationSystem.dao.IntervieweeMapper;
import com.lig.orientationSystem.domain.Resume;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.service.IntervieweeService;
import com.lig.orientationSystem.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Resume existedResume = intervieweeMapper.queryResume(resume.getPhoneNumber());
        if (existedResume!=null){
            if (Resume.thisTimeProject.equals(existedResume.getProject())){
                methodPassWrapper.setSuccess(false);
                methodPassWrapper.setDesc("一个项目每个手机号只能提交一次简历呦\n0.0");
                return methodPassWrapper;
            }
        }

        int existInterviewer = intervieweeMapper.selectInterviewerByStation(resume.getStation());
        if (existInterviewer == 0){
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("该岗位暂时没有面试官");
            return methodPassWrapper;
        }

        //数据库受影响的行数
        int insert = intervieweeMapper.insert(resume);
        boolean distributeResume = intervieweeMapper.distributeResume(resume.getResumeId(), resume.getStation());
        boolean updateResumeNumber = intervieweeMapper.updateResumeNumber(resume.getResumeId());

        //获取分发完简历面试官的user_ id,进行消息推送
        String userId = intervieweeMapper.selectUserId(resume.getResumeId());
        boolean sendMessage = interviewerService.sendMessage(userId, resume);
        interviewerService.addUndone(userId);

        if (insert != 1 && updateResumeNumber) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("简历写入失败");
            return methodPassWrapper;
        } else if (!distributeResume) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("简历分发失败");
            return methodPassWrapper;
        } else if (!sendMessage){
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("面试官推送失败");
            return methodPassWrapper;
        }
        methodPassWrapper.setSuccess(true);
        return methodPassWrapper;
    }

    public Resume queryResume(String phoneNumber) {
        Resume queryResume = intervieweeMapper.queryResume(phoneNumber);
        return queryResume;
    }


    //保存文件路径
    public void saveFileURL(String phoneNumber,String fileURL) {
        intervieweeMapper.saveFileURL(phoneNumber,fileURL);
    }
}
