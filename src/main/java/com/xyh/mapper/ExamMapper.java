package com.xyh.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Exam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.vo.request.admin.ExamPageReqVO;
import com.xyh.vo.request.student.ExamQueryReqVO;
import com.xyh.vo.request.teacher.ScoreReqVO;
import com.xyh.vo.response.admin.ExamPageRespVO;
import com.xyh.vo.response.other.KeyValue;
import com.xyh.vo.response.student.StuExamRespVO;
import com.xyh.vo.response.teacher.ScoreRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_exam】的数据库操作Mapper
* @createDate 2022-12-27 22:26:14
* @Entity com.xyh.pojo.Exam
*/
@Mapper
public interface ExamMapper extends BaseMapper<Exam> {

    IPage<ExamPageRespVO> getPageList(IPage<ExamPageRespVO> page, @Param("vo") ExamPageReqVO vo);

    com.xyh.vo.response.teacher.ExamRespVO selectExamById(Integer id);

    int delById(Integer id);

    List<StuExamRespVO> selectRecentExam(Integer classesId);

    IPage<StuExamRespVO> getStuExamList(IPage<ExamQueryReqVO> page, @Param("vo") ExamQueryReqVO vo);

    IPage<ScoreRespVO> selectScorePage(IPage<ScoreRespVO> page, @Param("vo") ScoreReqVO vo);

    List<KeyValue> getExamKVS();

}




