package com.lig.orientationSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryDataDTO implements Serializable {
    private String station;
    private int number;
    private String point;
}
