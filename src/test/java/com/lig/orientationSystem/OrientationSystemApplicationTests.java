package com.lig.orientationSystem;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.lig.orientationSystem.dao.AdministratorMapper;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.service.impl.AdministratorServiceImpl;
import com.lig.orientationSystem.service.impl.InterviewerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class OrientationSystemApplicationTests {
    @Autowired
    public OSS ossClient;

    @Test
    void testOSSClient() {
        ossClient.listBuckets().forEach( bucket -> {
            System.out.println(bucket.getName());
        });
    }


    @Autowired
    AdministratorServiceImpl administratorService;
    @Test
    void contextLoads() {
        MethodPassWrapper userId = administratorService.getUserId("19591318175");
        System.out.println(userId.toString());
    }

    @Test
    void test1() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("123");
        System.out.println(arrayList.size());
        System.out.println(arrayList.get(1));
    }
    @Autowired
    InterviewerServiceImpl interviewerService;
    @Test
    void test2() {
        interviewerService.delayEval();
    }

    @Test
    void test3() {
        administratorService.showDeliveryData();
    }
}
