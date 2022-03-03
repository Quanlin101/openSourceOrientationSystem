package com.lig.orientationSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
//    还是建一个list存？
    //岗位的序号，和前端对应，从一开始
//    private Integer number;
    //岗位的描述
    private String name;
    //岗位的要求
    private String desc;

    //初始化岗位列表
    //前端、产品、后端、运营、UI
    public static ArrayList<Station> initStationList(){

        ArrayList<Station> arrayList = new ArrayList<>();
        arrayList.add(new Station("前端","前端"));
        arrayList.add(new Station("产品","产品"));
        arrayList.add(new Station("后端","后端"));
        arrayList.add(new Station("运营","运营"));
        arrayList.add(new Station("UI","UI"));
        return arrayList;
    }
}
