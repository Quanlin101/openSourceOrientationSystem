package com.lig.orientationSystem.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@TableName("Interviewer")
public class Interviewer implements Serializable {
    //微信号
    @TableId
    @JsonIgnore
    private String userId;
    //面试官id,多表查询的时候用的id
    @JsonIgnore
    private Integer interviewerId;
    //电话号
    private String phoneNumber;
    //面试官姓名
    @TableField("`name`")
    private String name;
    //岗位
    private String station;
    //该面试官所有简历的数量
    private Integer resumeNumber;
    //面试官未完成简历数量
    @JsonIgnore
    private Integer undoneNumber;
    //该面试官未移交的所有简历 | 该面试官未处理的所有简历
    @TableField(exist = false)
    @JsonIgnore
    private Resume[] resumes;
    //面试官在该项目中拥有的转让简历的次数
    @TableField("`change`")
    private Integer change;
    //是否接收简历
    private boolean receive;

    //移交简历
    @TableField(exist = false)
    private String postPhoneNumber;
}
