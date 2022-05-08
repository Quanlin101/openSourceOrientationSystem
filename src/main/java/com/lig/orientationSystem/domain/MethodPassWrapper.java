package com.lig.orientationSystem.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


//用于controller层和service层的数据传递
@Data
@NoArgsConstructor
public class MethodPassWrapper<T> {
    private boolean success;
    private String desc;
    private T data;

}
