package com.xyh.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.vo.request.admin.AddQuestionPageReqVO;
import com.xyh.vo.response.admin.AddQuestionPageRespVO;
import com.xyh.vo.request.admin.QuestionPageReqVO;
import com.xyh.vo.response.admin.QuestionPageRespVO;
import com.xyh.vo.response.other.KeyValue;
import com.xyh.vo.response.teacher.AutoKeyValue;
import com.xyh.vo.response.teacher.QuestionItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_question】的数据库操作Mapper
* @createDate 2022-12-27 22:25:21
* @Entity com.xyh.pojo.Question
*/
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    List<KeyValue> getQuestionKeyValue();

    IPage<QuestionPageRespVO> getPageList(IPage<QuestionPageRespVO> page, @Param("vo") QuestionPageReqVO vo);

    void delByIds(List<Integer> ids);

    Question getPreview(Integer id);


    IPage<AddQuestionPageRespVO> getAddPageList(IPage<AddQuestionPageRespVO> page,@Param("vo")AddQuestionPageReqVO vo);

    List<QuestionItem> selectQuesItems(Integer id);

    List<Integer> selectIdList(Integer subjectId);

    /**
     * 最初做题的questionId
     * @param subjectId
     * @return
     */
    Integer selectStartId(Integer subjectId);

    List<Question> selectQuestionListByExamId(Integer examId);

    List<AutoKeyValue> selectQuestionCount(Integer subjectId);

    List<Question> selectByCondition(@Param("subjectId") Integer subjectId, @Param("type") int singleQuestion);

    Question selectByIds(@Param("questionId") Integer questionId, @Param("examId") Integer examId);
}




