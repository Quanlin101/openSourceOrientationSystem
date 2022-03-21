package com.lig.orientationSystem;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lig.orientationSystem.dao.AdministratorMapper;
import com.lig.orientationSystem.dao.IntervieweeMapper;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.domain.Resume;
import com.lig.orientationSystem.domain.Station;
import com.lig.orientationSystem.domain.StationList;
import com.lig.orientationSystem.service.impl.AdministratorServiceImpl;
import com.lig.orientationSystem.service.impl.IntervieweeServiceImpl;
import com.lig.orientationSystem.service.impl.InterviewerServiceImpl;
import com.lig.orientationSystem.service.impl.OssUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Autowired
    OssUtils ossUtils;

    @Test
    void test7() {
        Resume resume = new Resume();
        resume.setName("wql");
        MultipartFile file = null;
        MethodPassWrapper methodPassWrapper = ossUtils.upload(file,resume.getName());
        System.out.println(methodPassWrapper.getDesc());
    }
    @Test
    void test8(){
        administratorService.forceUpdate();
    }
    @Test
    void test9(){
        IPage<Resume> resumeIPage = administratorService.readResume(1, 5, "2022春招", "后端", 1);
        System.out.println(resumeIPage);
    }
}
