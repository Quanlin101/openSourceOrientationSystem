package com.lig.orientationSystem.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lig.orientationSystem.controller.dto.UserDTO;
import com.lig.orientationSystem.domain.Interviewer;
import com.lig.orientationSystem.domain.Resume;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InterviewerMapper extends BaseMapper<Interviewer> {
    //检验面试官是否被管理员授权可以处理简历
    @Select("select interviewer_id from interviewer where user_id = #{UserId}")
    Integer isExist(String UserId);

    //通过电话查面试官
    @Select("select * from interviewer where phone_number=#{phone_number}")
    Interviewer selectByPhoneNumber(String phoneNumber);
    @Select("select * from interviewer where interviewer_id = #{interviewerId}")
    Interviewer selectByInterviewerId(int interviewerId);


    //分页查看简历
    @Select("select * from resume where resume_id in " +
            "(select resume_id from interviewer_resume where " +
            "interviewer_id = (select interviewer_id from interviewer where user_id = #{userId})) " +
            "and checked = #{checked} and interview = #{interview} and project = #{project}")
    IPage<Resume> readResume(Page<Resume> resumePage, String userId, boolean checked, boolean interview, String project);

    //保存面评
    @Update("update resume set evaluation = #{evaluation}, pass = #{pass} where resume_id = resumeId")
    boolean saveEvaluation(int resumeId, String evaluation, boolean pass);

    //移交面评
    @Update("update interviewer_resume set interviewer_id = #{interviewerId} where resume_id = #{resumeId}")
    void changeInterviewer(int interviewerId, int resumeId);
    //修改管理员的移交次数
    @Update("update interviewer set `change` = #{change} where interviewer_id = #{interviewerId}")
    void updateChange(int interviewerId, int change);
    //增加面试官未处理简历数量
    @Update("update interviewer set undone_number = undone_number + 1 where user_id = #{userId}")
    void addUndone(String userId);
    @Select("select interviewer_id from interviewer_resume where resume_id = #{resumeId}")
    int selectInterviewerId(int resumeId);


    //设置已读
    @Update("update resume set checked = 1 where resume_id = #{resumeId}")
    void setRead(String resumeId);
    //设置已面试
    @Update("update resume set checked = 1 where resume_id = #{resumeId}")
    void setChecked(String resumeId);
    @Select("select * from `resume` where resume_id = #{resume_id}")
    Resume selectResumeById(String resumeId);



    //减少面试官未处理简历数量
    //移交时不会减少发起移交的面试官收到简历的总数
    @Update("update interviewer set undone_number = undone_number - 1 where phone_number = #{phone_number}")
    void downUndone(String phoneNumber);
    //移交简历时增加面试官未处理简历数量和收到简历总数
    @Update("update interviewer set undone_number = undone_number + 1, resume_number = resume_number + 1 where phone_number = #{phoneNumber}")
    void upUndone(String phoneNumber);

    //查找没有完成所有简历处理的面试官
    @Select("select user_id from interviewer where undone_number > 0")
    String[] selectUndonePerson();



}
