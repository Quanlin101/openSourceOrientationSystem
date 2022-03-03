package com.lig.orientationSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //开启定时的注解
public class OrientationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrientationSystemApplication.class, args);
    }

}
