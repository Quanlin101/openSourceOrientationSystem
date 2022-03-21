package com.lig.orientationSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResumeDTO implements Serializable {
    private String name;
    private int pass;
    private int notPass;
    private int all;
}
