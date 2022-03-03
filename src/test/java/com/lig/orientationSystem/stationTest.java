package com.lig.orientationSystem;

import com.lig.orientationSystem.domain.StationList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootTest
public class stationTest {
    StationList stationList = new StationList();

    @Test
    @Scheduled(initialDelay = 1000, fixedDelay = 5 * 1000)
    public void test(){
        System.out.println(stationList.arrayList.toString());
    }
}
