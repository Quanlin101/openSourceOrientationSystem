package com.lig.orientationSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataDetailsDTO implements Serializable {
    private String station;
    private int pass;
    private int noPass;
    private String point;
}
