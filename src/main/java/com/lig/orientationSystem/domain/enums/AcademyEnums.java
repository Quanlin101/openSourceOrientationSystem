package com.lig.orientationSystem.domain.enums;


public enum AcademyEnums {
    CommunicationEngineering(1,"通信工程"),
    ElectronicEngineering(2,"电子工程"),
    ComputerScienceAndTechnology(3,"计算机科学与技术"),
    MechanicalAndElectrical(4,"机电工程"),



    ;

    AcademyEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;
}
