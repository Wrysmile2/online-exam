package com.xyh.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.ExamQuestionAnswer;
import com.xyh.pojo.ExamRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.pojo.User;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.request.teacher.ToMarkExamReqVO;
import com.xyh.vo.response.other.KeyValue;
import com.xyh.vo.response.student.ChartVO;
import com.xyh.vo.response.student.RecordRespVO;
import com.xyh.vo.response.teacher.ScoreDetailRespVO;
import com.xyh.vo.response.teacher.ToMarExamRecordRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_exam_record】的数据库操作Mapper
* @createDate 2023-02-27 12:05:59
* @Entity com.xyh.pojo.ExamRecord
*/
@Mapper
public interface ExamRecordMapper extends BaseMapper<ExamRecord> {

    IPage<RecordRespVO> selectPageList(IPage<RecordRespVO> page,@Param("vo") QueryReqVO vo);

    List<ScoreDetailRespVO> selectScoreList(@Param("examId") Integer examId, @Param("classesId") Integer classesId);

    IPage<ToMarExamRecordRespVO> selectMarkPageList(IPage<ToMarExamRecordRespVO> page, @Param("vo") ToMarkExamReqVO vo);

    List<KeyValue> selectKVS(@Param("vo") User user);

    void updateBatch(List<ExamQuestionAnswer> answerList);
}




