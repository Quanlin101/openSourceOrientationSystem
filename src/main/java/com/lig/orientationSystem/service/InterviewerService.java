package com.lig.orientationSystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lig.orientationSystem.domain.Interviewer;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.domain.Resume;

public interface InterviewerService extends IService<Interviewer> {
    //面试官是否存在
    Integer isExist(String UserId);

    //分页查看简历
    IPage<Resume> readResume(String userId, int current, int size, int status);

    //写面评 + 是否通过
    MethodPassWrapper saveEvaluation(int resumeId, String evaluation, boolean pass);

    //移交简历
    MethodPassWrapper changeInterviewer(String postPhoneNumber, String phoneNumber, int resumeId, String userId);

    //WX推送消息收到简历的消息
    boolean sendMessage(String userId, Resume resume);

    //定时推送仍有未处理简历的面试官 没查看没面评的都推送！
    void delayEval();

    //设置已读
    void setRead(String resumeId);

    //设置已面试
    MethodPassWrapper setCheck(int resumeId);

    //添加未处理的简历数量
    void addUndone(String userId);
}
