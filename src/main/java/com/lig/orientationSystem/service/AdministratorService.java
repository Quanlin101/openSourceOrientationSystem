package com.lig.orientationSystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lig.orientationSystem.controller.dto.*;
import com.lig.orientationSystem.domain.*;

import java.util.ArrayList;

public interface AdministratorService extends IService<Administrator> {

    //数据看板
    //投递数据
    ArrayList<DeliveryDataDTO> showDeliveryData();

    //详情
    ArrayList<DataDetailsDTO> showDetails();

    //信息来源
    ArrayList<MessageFromDTO> showMessageFrom();

    //简历管理 简历查看，状态查看
    //把所以简历的数据全传过去？还是每次都分页查？
    //简历查看
    IPage<Resume> readResume(int current, int size, String projectName, String station, int status);

    //面试官管理 增删改面试官信息，是否接收简历， 接收简历数？
    //查看面试官
    IPage readInterviewer(int current, int size, String station, int status);

    //添加面试官（获取userid）
    MethodPassWrapper getUserId(String phoneNumber);

    //添加面试官
    MethodPassWrapper addInterviewer(Interviewer interviewer);

    //删除面试官
    MethodPassWrapper deleteInterviewer(String phoneNumber);

    //修改面试官信息 （姓名，岗位，手机号）
    void changeInterviewer(Interviewer newInfo);

    //修改面试官是否能接收简历
    void isReceiveResume(String phoneNumber, Boolean isReceive);

    //添加项目
    MethodPassWrapper addProject(String projectName);

    //展示项目
    ArrayList<ProjectDTO> showProject();

    //上下移动编辑岗位
    MethodPassWrapper changeStation(int code, int number);
    //编辑岗位
    MethodPassWrapper changeInner(String postName, String name, String desc);

    //增加岗位
    MethodPassWrapper addStation(Station station);

    //项目更新时更新面试官移交简历次数
    void refreshChange();

    //内推人数据面板
    ArrayList<PushInCountDTO> showPushInMan();


}
