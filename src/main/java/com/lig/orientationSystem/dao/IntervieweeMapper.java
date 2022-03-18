package com.lig.orientationSystem.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lig.orientationSystem.domain.Interviewer;
import com.lig.orientationSystem.domain.Resume;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IntervieweeMapper extends BaseMapper<Resume> {
    @Insert("insert into practice values(#{phoneNumber}, #{file})")
    void insertFile(String phoneNumber, byte[] file);
    //查看该岗位是否有面试官
    @Select("select count(*) from `interviewer` where `station` = #{station} and receive = 1")
    int selectInterviewerByStation(String station);

//    @Select("((SELECT MIN(interviewer_id) FROM ( SELECT interviewer_id FROM interviewer WHERE resume_number = (SELECT MIN(resume_number) FROM (SELECT * FROM interviewer) t1 where receive = 1 and station = #{station})) t2 ))")
//    Interviewer getProperInterviewer();

    //保存并自动分发
    @Insert("INSERT INTO interviewer_resume   VALUES(((SELECT MIN(interviewer_id) FROM ( SELECT interviewer_id FROM interviewer WHERE resume_number = (SELECT MIN(resume_number) FROM interviewer where receive = 1 and station = #{station}) and receive = 1 and station = #{station}) t2 )),  #{resumeId})")
    boolean distributeResume(int resumeId, String station);
    //分发更新面试官的简历数量
    @Update("update interviewer set resume_number = resume_number + 1, undone_number = undone_number + 1 where interviewer_id = (select interviewer_id from interviewer_resume where resume_id = #{resumeId})")
    boolean updateResumeNumber(int resumeId);

    @Select("select * from resume where `phone_number`= #{phoneNumber} and project = #{project}")
    Resume queryResume(String phoneNumber,String project);

    //保存文件url
    @Update("update resume set fileURL = #{fileURL} where phone_number = #{phoneNumber}")
    void saveFileURL(String phoneNumber, String fileURL);
    @Select("select user_id from interviewer where interviewer_id = (select interviewer_id from interviewer_resume where resume_id = #{resumeId}) ")
    String selectUserId(Integer resumeId);


}
