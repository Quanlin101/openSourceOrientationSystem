package com.lig.orientationSystem.config;

import com.lig.orientationSystem.domain.Station;
import com.lig.orientationSystem.domain.StationList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class StationConfig {
    StationList stationArrayList = new StationList();
    @Bean
    public ArrayList<Station> init(){
        return stationArrayList.arrayList;
    }
}
