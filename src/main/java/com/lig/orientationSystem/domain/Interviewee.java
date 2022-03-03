//package com.lig.orientationSystem.domain;
//
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.lig.orientationSystem.domain.enums.ClassEnums;
//import com.lig.orientationSystem.domain.enums.MessageFromEnums;
//import com.lig.orientationSystem.domain.enums.SexEnums;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.Serializable;
//import java.util.Date;
//
//
////封装投递内容
//@NoArgsConstructor
//@Data
//@TableName("Interviewee")
//public class Interviewee implements Serializable {
//    //    包括
////a.从哪里了解到我们的：海报宣传、宣讲会、微信推文、QQ自媒体，冬/夏令营
////b.内推人：文本输入框 底部灰字没有可不填
////c.姓名-文本框输入
////d.性别-选择男女
////e.年级-选择”大一“”大二“”大三“”其他“
////f.学院及专业、联系电话、QQ都是文本输入框
////g.是否有相关项目/实习经历
//
//    //电话
//    @TableId
//    private String phoneNumber;
//    //了解为之信息来源
//    private String messageFrom;
//    //内推人
//    private String pushInPerson;
//    //姓名
//    private String name;
//    //性别
//    private String gender;
//    //学院
//    private String academy;
//    //专业
//    private String major;
//    //年级
//    private String grade;
//    //自述（实习经历）
//    private String practice;
//    //提交简历时间
//    private Date date;
//    //QQ
//    private String qq;
//}
