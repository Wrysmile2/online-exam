package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.ExamRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.pojo.User;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.request.teacher.ToMarkExamReqVO;
import com.xyh.vo.response.student.ChartVO;
import com.xyh.vo.response.student.ExamViewVO;
import com.xyh.vo.response.student.RecordRespVO;
import com.xyh.vo.response.teacher.ScoreDetailRespVO;
import com.xyh.vo.response.teacher.ToMarExamRecordRespVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_exam_record】的数据库操作Service
* @createDate 2023-02-27 12:05:59
*/
public interface ExamRecordService extends IService<ExamRecord> {

    IPage<RecordRespVO> selectPageList(QueryReqVO vo);

    ExamViewVO selectExamView(Integer examId, Integer userId);

    List<ScoreDetailRespVO> selectScoreList(Integer examId, Integer classesId);

    IPage<ToMarExamRecordRespVO> selectToMarkPageList(ToMarkExamReqVO vo);

    ChartVO selectIndexChart(User user);

    ExamViewVO selectRecordById(Integer id);

    void correct(ExamViewVO vo);
}
