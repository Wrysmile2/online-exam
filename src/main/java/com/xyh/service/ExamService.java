package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Exam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.vo.request.admin.ExamPageReqVO;
import com.xyh.vo.request.student.ExamQueryReqVO;
import com.xyh.vo.request.student.ExamQuesReqVO;
import com.xyh.vo.request.teacher.AutoGroupReqVO;
import com.xyh.vo.request.teacher.ScoreReqVO;
import com.xyh.vo.response.admin.ExamPageRespVO;
import com.xyh.vo.response.student.StuExamRespVO;
import com.xyh.vo.response.teacher.ScoreRespVO;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_exam】的数据库操作Service
* @createDate 2022-12-27 22:26:14
*/

public interface ExamService extends IService<Exam> {

    IPage<ExamPageRespVO> pageList(ExamPageReqVO vo);

    com.xyh.vo.response.teacher.ExamRespVO selectExamById(Integer id);

    Integer editOrAdd(com.xyh.vo.response.teacher.ExamRespVO vo, Integer createUser);

    boolean delById(Integer id);

    List<StuExamRespVO> selectRecentExam(Integer classesId,Integer userId);

    IPage<StuExamRespVO> selectStudentExam(ExamQueryReqVO vo,Integer userId);

    Double correctExam(ExamQuesReqVO vo, Integer userId);

    String autoGroup(AutoGroupReqVO vo,Integer userId);

    IPage<ScoreRespVO> selectScorePage(ScoreReqVO vo);
}
