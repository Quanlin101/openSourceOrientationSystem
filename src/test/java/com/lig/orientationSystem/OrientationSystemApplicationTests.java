package com.lig.orientationSystem;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.lig.orientationSystem.dao.AdministratorMapper;
import com.lig.orientationSystem.dao.IntervieweeMapper;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.domain.Resume;
import com.lig.orientationSystem.domain.Station;
import com.lig.orientationSystem.domain.StationList;
import com.lig.orientationSystem.service.impl.AdministratorServiceImpl;
import com.lig.orientationSystem.service.impl.IntervieweeServiceImpl;
import com.lig.orientationSystem.service.impl.InterviewerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
class OrientationSystemApplicationTests {
    @Autowired
    public OSS ossClient;

    @Test
    void testOSSClient() {
        ossClient.listBuckets().forEach(bucket -> {
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

    @Test
    void test4() {
        interviewerService.delayEval();
    }

    @Autowired
    IntervieweeMapper intervieweeMapper;
    @Autowired
    IntervieweeServiceImpl intervieweeService;

    @Test
    void test5() {
        Resume resume = new Resume();
        resume.setSubmit_time(new Date());
        resume.setGender("1");
        resume.setGrade("大一");
        resume.setHasPractice(false);
        resume.setMajor("软工");
        resume.setMessageFrom("春招");
        resume.setName("wql");
        resume.setPractice("0.0");
        resume.setStation("后端");
        intervieweeService.submit(resume);
//        intervieweeMapper.distributeResume(1, "后端");
    }
    @Test
    void test6() {
        Station station = new Station();
        System.out.println(StationList.arrayList);
        station.setName("运营");
        administratorService.deleteStation(station);
        System.out.println(StationList.arrayList);
    }
}
