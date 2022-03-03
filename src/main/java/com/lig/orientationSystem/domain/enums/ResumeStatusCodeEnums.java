package com.lig.orientationSystem.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ResumeStatusCodeEnums{

    WaitingToSee(1, "等待查看"),
    WaitingForInterview(2,"等待面试"),
    Pass (3, "不通过"),
    Fail(4,"通过")
    ;

    ResumeStatusCodeEnums(int code, String desc) {
        this.code=code;
        this.desc=desc;
    }
//@JsonValue
//可以用在get方法或者属性字段上，一个类只能用一个，当加上@JsonValue注解时，
// 该类的json化结果，只有这个get方法的返回值，而不是这个类的属性键值对
    @EnumValue
    private final int code;
    @JsonValue
    private final String desc;


}
