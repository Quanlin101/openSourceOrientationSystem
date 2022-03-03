package com.lig.orientationSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class R<T> implements Serializable {

    public interface ResultCode {
        Integer SUCCESS = 1;
        Integer ERROR = -1;
    }

    private Boolean success;
    private Integer code;
    private String message;
    private T data;

    private R(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public static <T> R<T> ok() {
        return new R<>(true, ResultCode.SUCCESS, "成功");
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>(true, ResultCode.SUCCESS, "成功");
        r.setData(data);
        return r;
    }

    public static <T> R<T> error() {
        return new R<>(false, ResultCode.ERROR, "失败");
    }

    public static <T> R<T> error(String str) {
        return new R<>(false, ResultCode.ERROR, str);
    }

}
