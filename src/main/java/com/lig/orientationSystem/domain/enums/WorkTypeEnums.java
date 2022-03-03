package com.lig.orientationSystem.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum WorkTypeEnums {
//    产品经理，运营，前端开发，后端开发，运维，UI设计师
    ProductManager(1,"产品经理"),
    Operator(2,"运营"),
    frontEndDeveloper(3,"前端开发"),
    backEndDeveloper(4,"后端开发"),
    OperationAndMaintenance(5,"运维"),
    UIDesigner(6,"UI设计师"),
    Others(7,"待增加");
    ;

    @EnumValue
    public final int code;
    @JsonValue
    public final String desc;
    WorkTypeEnums(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

}
