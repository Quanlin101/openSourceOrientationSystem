package com.lig.orientationSystem.config;

import com.lig.orientationSystem.domain.Station;
import com.lig.orientationSystem.domain.StationList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;


//岗位信息的改动只在内存生效，数据库中没表 服务器重启回返回到初始状态
//更改岗位并确定长期不变的话建议在station的list初始化那里进行修改
@Configuration
public class StationConfig {
    StationList stationArrayList = new StationList();
    @Bean
    public ArrayList<Station> init(){
        return stationArrayList.arrayList;
    }
}
