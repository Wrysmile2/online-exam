package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.pojo.QuestionPractice;
import com.xyh.vo.request.student.PracticeQuesReqVO;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.response.student.AnswerCardRespVO;
import com.xyh.vo.response.student.PracticeListRespVO;
import com.xyh.vo.response.student.PracticeQuestionRespVO;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_question_practice】的数据库操作Service
* @createDate 2022-12-27 22:22:44
*/
public interface QuestionPracticeService extends IService<QuestionPractice> {

    IPage<PracticeListRespVO> selectPracticeList(QueryReqVO vo);

    List<AnswerCardRespVO> selectCard(Integer subjectId, Integer userId);

    PracticeQuestionRespVO selectPracticeQuestion(PracticeQuesReqVO vo);

    Boolean correctQuestion(PracticeQuestionRespVO vo,Integer createUser);
}
