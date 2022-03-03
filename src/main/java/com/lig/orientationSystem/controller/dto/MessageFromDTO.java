package com.lig.orientationSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageFromDTO implements Serializable {
    private String messageFrom;
    private int number;
    private String point;
}
