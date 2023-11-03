package com.xyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.QuestionPractice;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.response.student.PracticeListRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* @author xyh
* @description 针对表【tbl_question_practice】的数据库操作Mapper
* @createDate 2022-12-27 22:22:44
* @Entity com.xyh.pojo.QuestionPractice
*/
@Mapper
public interface QuestionPracticeMapper extends BaseMapper<QuestionPractice> {


    IPage<PracticeListRespVO> selectPracticeList(IPage<PracticeListRespVO> page, @Param("vo") QueryReqVO vo);

    @Select("select correct from tbl_question_practice where question_id = #{questionId} and create_user = #{userId}")
    Boolean selectUserAnswerCard(@Param("questionId") Integer questionId, @Param("userId") Integer userId);

    Integer selectLastId(@Param("userId") Integer userId, @Param("subjectId") Integer subjectId);

    QuestionPractice selectByQuestionId(@Param("questionId") Integer questionId, @Param("userId") Integer userId);

}
