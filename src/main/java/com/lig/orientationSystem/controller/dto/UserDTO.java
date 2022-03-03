package com.lig.orientationSystem.controller.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class UserDTO implements Serializable {
    private String userName;
    private String password;
    private Integer id;
}
