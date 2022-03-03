package com.lig.orientationSystem.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SexEnums {
    MAN(1, "男"),
    WOMAN(2, "女");

    @EnumValue
    private Integer code;
    @JsonValue
    private String desc;

    SexEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
