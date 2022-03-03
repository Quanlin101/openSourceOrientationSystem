package com.lig.orientationSystem.domain.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ClassEnums {
    Freshman(1,"大一"),
    Sophomore(2,"大二"),
    Junior(3,"大三"),
    Senior(4,"其他"),
    ;

    //创建枚举类，在需要存储数据库的属性上添加@EnumValue注解，在需要前端展示的属性上添加@JsonValue注解；
    @EnumValue
    private final Integer code;
    @JsonValue
    private final String desc;
    ClassEnums(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
