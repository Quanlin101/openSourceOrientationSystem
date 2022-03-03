package com.lig.orientationSystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lig.orientationSystem.domain.enums.ResumeStatusCodeEnums;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

//@TableField
//1.指定数据表中字段名（当表中数据和实体中数据不同时，设置 value="数据库表中名称" 完成正确映射）
//2.当实体中的字段数据库中并不存在，可以设置 exist=false 在进行数据库操作时排除该字段
//3.不希望某些字段被查出，可以设置 select=false 保护该字段在查询时不被查出
@NoArgsConstructor
@Data
@TableName("resume")
public class Resume implements Serializable {
    public static String thisTimeProject = "2022春招";
    //简历的id
    @TableId(type = IdType.AUTO)
    private Integer resumeId;
    //面试官 使用masterId确定
    @TableField(exist = false)
    @JsonIgnore
    private Interviewer interviewer;
    //投递者电话
    private String phoneNumber;
    //了解为之信息来源
    private String messageFrom;
    //内推人
    private String pushInMan;
    //姓名
    @TableField("`name`")
    private String name;
    //性别
    private String gender;
    //学院和专业
    private String major;
    //年级
    private String grade;
    //意向岗位
    private String station;
    //是否有实习经历
    private boolean hasPractice;
    //自述（实习经历）
    private String practice;
    //实习经历路径
    private String fileURL;
    //提交简历时间
    private Date submit_time;
    //QQ
    private String qq;
    //简历所属项目
    private String project=Resume.thisTimeProject;


    //面试官是否查看
    private boolean checked;
    //是否写了面评
    private boolean interview;
    //面评
    private String evaluation;
    //是否通过
    private boolean pass;
}
