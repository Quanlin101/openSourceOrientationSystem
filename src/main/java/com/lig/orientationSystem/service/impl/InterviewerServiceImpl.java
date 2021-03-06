package com.lig.orientationSystem.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lig.orientationSystem.dao.AdministratorMapper;
import com.lig.orientationSystem.dao.InterviewerMapper;
import com.lig.orientationSystem.domain.Interviewer;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.domain.Resume;
import com.lig.orientationSystem.service.InterviewerService;
import com.lig.orientationSystem.until.AccessTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Slf4j
@Transactional
@Service
public class InterviewerServiceImpl extends ServiceImpl<InterviewerMapper, Interviewer> implements InterviewerService {
    @Autowired
    InterviewerMapper interviewerMapper;

    static MethodPassWrapper methodPassWrapper = new MethodPassWrapper<>();


    //面试官是否存在
    public Integer isExist(String UserId) {
        return interviewerMapper.isExist(UserId);
    }

    //分页查看简历
    public IPage<Resume> readResume(String userId, int current, int size, int status) {
//        interviewerMapper.readResume(interviewerId);
        boolean checked;
        boolean interview;

        Page page = new Page(current, size);
        if (status == 1) {
            checked = false;
            interview = false;
        } else if (status == 2) {
            checked = true;
            interview = false;
        } else {
            checked = true;
            interview = true;
        }
        IPage resumePage = interviewerMapper.readResume(page, userId, checked, interview, Resume.thisTimeProject);
        return resumePage;
    }

    //设置已读
    public void setRead(String resumeId) {

        interviewerMapper.setRead(resumeId);
    }

    //面评前跳转获取简历信息
    public MethodPassWrapper getResume(int resumeId) {
        Resume resume = interviewerMapper.selectResumeById(resumeId);
        if (resume == null){
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("简历不存在Ove");
            return methodPassWrapper;
        }
        else {
            methodPassWrapper.setSuccess(true);
            methodPassWrapper.setData(resume);
            return methodPassWrapper;
        }
    }


    //写面评 + 是否通过
    public MethodPassWrapper saveEvaluation(int resumeId, String evaluation, boolean pass) {
        if (evaluation == null) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("面评不能为空");
            log.warn("面评为空，应该前端判断0.0");
            return methodPassWrapper;
        } else {
            boolean saveEvaluation = interviewerMapper.saveEvaluation(resumeId, evaluation, pass);

            if (!saveEvaluation) {
                methodPassWrapper.setSuccess(false);
                log.error("面评持久到数据库中失败，去查SQL resumeId:{}",resumeId);
                methodPassWrapper.setDesc("持久化面评失败");
                return methodPassWrapper;
            }
        }
        methodPassWrapper.setSuccess(true);
        interviewerMapper.downUndone(interviewerMapper.selectByInterviewerId(interviewerMapper.selectInterviewerId(resumeId)).getPhoneNumber());
        return methodPassWrapper;
    }

    public Interviewer getInterviewerByUserId(String userId) {
        Interviewer interviewer = interviewerMapper.selectById(userId);
        return interviewer;
    }

    @Transactional
    //移交简历
    public MethodPassWrapper changeInterviewer(String postPhoneNumber, String phoneNumber, int resumeId) {
        Interviewer interviewer = interviewerMapper.selectByPhoneNumber(postPhoneNumber);
        Interviewer newInterviewer = interviewerMapper.selectByPhoneNumber(phoneNumber);
        Resume resume = interviewerMapper.selectResumeById(resumeId);

        boolean send = sendMessage(newInterviewer.getUserId(),resume);

        if (!send){
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("移交简历无法推送:(");
            log.error("面试官推送简历接口失效，检查推送配置 resumeId：{}",resumeId);
            return methodPassWrapper;
        }

        int change = interviewer.getChange();
        if (change > 0) {
//            System.out.println(newInterviewer.getInterviewerId());
            interviewerMapper.changeInterviewer(newInterviewer.getInterviewerId(), resumeId);
            change = change - 1;
            interviewerMapper.updateChange(interviewer.getInterviewerId(), change);
            interviewerMapper.upUndone(phoneNumber);
            interviewerMapper.downUndone(postPhoneNumber);
        } else {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("您已经没有转送机会:)");
            return methodPassWrapper;
        }
        methodPassWrapper.setSuccess(true);
        return methodPassWrapper;
    }

    @Value("${wx.agentid}")
    String agentid;
    @Autowired
    RestTemplate restTemplate;

    //WX推送消息收到简历的消息
    public boolean sendMessage(String userId, Resume resume) {
        JSONObject contents = new JSONObject();
        String gender = resume.getGender();
        if ("1".equals(gender.toString())) {
            gender = "男";
        } else {
            gender = "女";
        }
        if (resume.isHasPractice()){
            contents.put("content", "您收到一份简历，请及时处理。" +
                    "\n姓名:" + resume.getName() +
                    "\n性别:" + gender +
                    "\n年级:" + resume.getGrade() +
                    "\n手机号:" + resume.getPhoneNumber() +
                    "\nQQ:" + resume.getQq() +
                    "\n自述:" + resume.getPractice() +
                    "\n<a href=\""+ resume.getFileURL() + "\">点击查看项目经历</a>" +
                    "\n<a href=\"https://orientation-system.wizzstudio.com/#/interviewer/read?resumeId=" + resume.getResumeId() + "\">点击已查看</a>" +
                    "\n<a href=\"https://orientation-system.wizzstudio.com/#/interviewer/fill?" + "resumeId=" + resume.getResumeId() + "\">点击面评</a>"

            );
        }
        else {
            contents.put("content", "您收到一份简历，请及时处理。" +
                    "\n姓名:" + resume.getName() +
                    "\n性别:" + gender +
                    "\n年级:" + resume.getGrade() +
                    "\n手机号:" + resume.getPhoneNumber() +
                    "\nQQ:" + resume.getQq() +
                    "\n自述:" + resume.getPractice() +
                    "\n<a href=\"https://orientation-system.wizzstudio.com/#/interviewer/read?resumeId=" + resume.getResumeId() + "\">点击已查看</a>" +
                    "\n<a href=\"https://orientation-system.wizzstudio.com/#/interviewer/fill?"+ "resumeId=" + resume.getResumeId()  + "\">点击面评</a>"

            );
        }

        String access_token = AccessTokenUtils.access_token;


        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="
                + access_token;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", userId);
        jsonObject.put("msgtype", "text");
        jsonObject.put("agentid", agentid);
        jsonObject.put("text", contents);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, jsonObject.toString(), String.class);
        String body = responseEntity.getBody();
        JSONObject jsonBody = JSONObject.parseObject(body);
        String errcode = jsonBody.getString("errcode");
        if (!"0".equals(errcode)) {
            log.error("消息推送wx返回错误代码 jsonBody:{}",jsonBody);
            return false;
        }
        return true;
    }

    //定时推送仍有未处理简历的面试官 没查看没面评的都推送！ 附加： 更新有所有面试官未处理简历数量
    @Autowired
    AdministratorMapper administratorMapper;
    @Scheduled(cron = "0 0 8 * * ?")
    public void delayEval() {
        //附加： 更新有所有面试官未处理简历数量
        ArrayList<String> userIdList = administratorMapper.getAllUserId();
        for (String userId: userIdList) {
            administratorMapper.resetUndoneNumber(userId);
        }

        String access_token = AccessTokenUtils.access_token;
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="
                + access_token;
        JSONObject content = new JSONObject();
        content.put("content", "您有尚未处理的简历，请及时处理" +
                "\n:)");
        JSONObject jsonObject = new JSONObject();
        String[] users = interviewerMapper.selectUndonePerson();
        String userString = "";
        for (String user : users) {
            userString = userString.concat(user + "|");
        }
//        System.out.println(userString);
        if ("".equals(userString)) {
//            System.out.println("全部完成辣");
            return;
        }
        userString = userString.substring(0, userString.length() - 1);
        jsonObject.put("touser", userString);
        jsonObject.put("msgtype", "text");
        jsonObject.put("agentid", agentid);
        jsonObject.put("text", content);
//        System.out.println(jsonObject.toString());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, jsonObject.toString(), String.class);
//        System.out.println(responseEntity);
        log.info("8:00定时推送");
    }


    //设置已面试
    public MethodPassWrapper setCheck(int resumeId) {
        Resume resume = interviewerMapper.selectResumeById(resumeId);
        if (resume == null) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("简历id异常，已读更新失败");
            log.error("简历设置已面试失败 resumeId:{}",resumeId);
            return methodPassWrapper;
        }
        interviewerMapper.setChecked(resumeId);
        resume = interviewerMapper.selectResumeById(resumeId);
        if (!resume.isChecked()) {
            methodPassWrapper.setSuccess(false);
            methodPassWrapper.setDesc("持久化数据异常，已读更新失败");
            log.error("数据库更改resume的checked失败 amazing resumeId:{}",resumeId);
            return methodPassWrapper;
        }
        methodPassWrapper.setSuccess(true);
        return methodPassWrapper;
    }

    //添加未处理的简历数量
    public void addUndone(String userId) {
        interviewerMapper.addUndone(userId);
    }


}
