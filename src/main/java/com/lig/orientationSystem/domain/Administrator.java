package com.lig.orientationSystem.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lig.orientationSystem.controller.dto.R;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

@Data
@NoArgsConstructor
@TableName("Administrator")
public class Administrator implements Serializable {
    //0号用户
    public int userId;
}
