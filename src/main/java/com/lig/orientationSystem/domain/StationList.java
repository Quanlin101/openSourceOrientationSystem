package com.lig.orientationSystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

//项目的全局变量 岗位列表
public class StationList {
    public static ArrayList<Station> arrayList = Station.initStationList();

    /**
     * 检验岗位是否存在
     * @param name
     * @return  true： 存在 、 false： 不存在
     */
    public boolean exist(String name){
        for (Station station : arrayList) {
            if (station.getName().equals(name))
                return true;
        }
        return false;
    }

    /**
     * 添加岗位
     * @param station
     */
    public void add(Station station){
        arrayList.add(station);
    }

    public boolean change(String postName, Station NewStation){
        for (int i=0; i < arrayList.size();++i){
            if (arrayList.get(i).getName().equals(postName)){
                arrayList.set(i,NewStation);
                return true;
            }
        }
        return false;
    }

    /***
     *上移和下移只操作服务器里的数据，序号由station在stationList的位置确定
     * 用名字（name）做表示！
     * @param number 上移岗位的序号
     */
    public void up(int number){
        Station mid = arrayList.get(number-1);
        arrayList.set(number-1,arrayList.get(number-2));
        arrayList.set(number-2,mid);
    }
    //下移
    public void down(int number){
        Station mid = arrayList.get(number-1);
        arrayList.set(number-1,arrayList.get(number));
        arrayList.set(number,mid);
    }

    /***
     * 编辑岗位内部信息，要修改数据库！  感觉有些不合理的地方，记得交流！
     * @param number
     * @param changed
     */
    public void change(int number, Station changed){
        arrayList.set(number-1, changed);
    }
}
