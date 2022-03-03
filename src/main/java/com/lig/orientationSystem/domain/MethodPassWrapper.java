package com.lig.orientationSystem.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MethodPassWrapper<T> {
    private boolean success;
    private String desc;
    private T data;

}
