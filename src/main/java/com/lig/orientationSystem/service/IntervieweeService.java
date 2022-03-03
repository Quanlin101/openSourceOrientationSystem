package com.lig.orientationSystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lig.orientationSystem.dao.IntervieweeMapper;
import com.lig.orientationSystem.domain.MethodPassWrapper;
import com.lig.orientationSystem.domain.Resume;
import com.lig.orientationSystem.service.impl.InterviewerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public interface IntervieweeService extends IService<Resume> {
    //保存简历
    MethodPassWrapper submit(Resume resume);

    //查询简历
    Resume queryResume(String phoneNumber) ;

    //保存文件路径
    void saveFileURL(String phoneNumber,String fileURL);
}
