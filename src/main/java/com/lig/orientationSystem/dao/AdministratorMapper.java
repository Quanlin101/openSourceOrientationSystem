package com.lig.orientationSystem.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lig.orientationSystem.controller.dto.DeliveryDataDTO;
import com.lig.orientationSystem.controller.dto.ProjectDTO;
import com.lig.orientationSystem.controller.dto.PushInCountDTO;
import com.lig.orientationSystem.domain.Administrator;
import com.lig.orientationSystem.domain.Interviewer;
import com.lig.orientationSystem.domain.Resume;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


@Mapper
public interface AdministratorMapper extends BaseMapper<Administrator> {

    //查看简历
    @Select("select * from resume where project = #{project} and station = #{station}")
    IPage<Resume> readResumeAllStatus(Page<Resume> page, String station, String project);
    @Select("select * from resume " +
            "where project = #{project} and station = #{station} " +
            "and checked = #{checked} and interview = #{interview} and pass = #{pass}")
    IPage<Resume> readResume(Page<Resume> page, String project, String station, boolean checked, boolean interview, boolean pass);

    //查看面试官
    @Select("select name, phone_number, station,receive, resume_number from interviewer" +
            " where  station = #{station} and receive = #{receive}")
    IPage<Interviewer> readInterviewer(Page<Interviewer> page, String station, boolean receive);
    //全状态
    @Select("select name, phone_number, station,receive, resume_number from interviewer" +
            " where  station = #{station} ")
    IPage<Interviewer> readInterviewerAllReceive(Page<Interviewer> page,  String station);

    //添加面试官
    @Insert("insert into interviewer(user_id, phone_number, name, station) values(#{UserId}, #{phoneNumber}, #{name}, #{station})")
    void addInterviewer(String UserId, String phoneNumber,String name, String station);

    //删除面试官
    @Delete("delete from interviewer where phone_number = #{phoneNumber}")
    boolean deleteInterviewer(String phoneNumber);

    //修改面试官是否能被分发简历
    @Update("update interviewer set receive = #{isReceive} where phone_number = #{phoneNumber} ")
    boolean isReceiveResume(String phoneNumber, Boolean isReceive);

    //通过电话查面试官
    @Select("select * from interviewer where phone_number = #{phoneNumber}")
    Interviewer selectByPhone(String phoneNumber);

    //修改面试官数据
    @Update("update interviewer set name = #{name}, station = #{station}, phone_number = #{phoneNumber} where phone_number = #{postPhoneNumber}")
    boolean changeInterviewer(String postPhoneNumber, String name, String station, String phoneNumber);

    //添加项目
    @Insert("insert into project values(#{project})")
    void addProject(String project);

    //查询所有项目
    @Select("select * from project")
    ArrayList<String> selectAllProject();

    //查询项目详情
    @Select("select count(*) from resume where project = #{project} and pass = 0")
    int selectProjectNoPAss(String project);
    @Select("select count(*) from resume where project = #{project} and pass = 1")
    int selectProjectPass(String project);
    //验证添加
    @Select("select count(*) from project where project = #{projectName}")
    int selectProject(String projectName);

    //项目更新，重置转发简历数量
    @Update("UPDATE interviewer SET `change` = 3")
    void refreshChange();

    //展示投送数据
    @Select("select count(*) from resume where station = #{station}")
    int showDeliveryData(String station);
    @Select("select count(*) from `resume`")
    int selectTotal();

    //详细数据展示
    @Select("select count(*) from `resume` where station = #{station} and `pass` = 0")
    Integer getNoPass(String station);
    @Select("select count(*) from `resume` where station = #{station} and `pass` = 1")
    Integer getPass(String station);
    //展示信息来源面板
    @Select("select count(*) from `resume` where message_from = #{messageFrom}")
    Integer selectMessageNumber(String messageFrom);
    //展示内推人信息面板
    @Select("SELECT COUNT(*) `count`, push_in_man FROM `resume` GROUP BY push_in_man")
    ArrayList<PushInCountDTO> selectPushInMan();


}
