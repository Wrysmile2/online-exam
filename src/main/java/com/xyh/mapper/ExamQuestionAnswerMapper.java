package com.xyh.mapper;

import com.xyh.pojo.ExamQuestionAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.vo.response.student.ExamViewAnswerItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_exam_question_answer】的数据库操作Mapper
* @createDate 2022-12-27 22:26:14
* @Entity com.xyh.pojo.ExamQuestionAnswer
*/
@Mapper
public interface ExamQuestionAnswerMapper extends BaseMapper<ExamQuestionAnswer> {

    List<ExamViewAnswerItem> selectExamViewList(@Param("userId") Integer userId,@Param("examId")Integer examId);
}




