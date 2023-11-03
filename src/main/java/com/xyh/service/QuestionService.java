package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.vo.request.admin.AddQuestionPageReqVO;
import com.xyh.vo.response.admin.AddQuestionPageRespVO;
import com.xyh.vo.request.admin.QuestionPageReqVO;
import com.xyh.vo.response.admin.QuestionPageRespVO;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_question】的数据库操作Service
* @createDate 2022-12-27 22:25:21
*/
public interface QuestionService extends IService<Question> {

    IPage<QuestionPageRespVO> getPageList(QuestionPageReqVO vo);

    void delByIds(List<Integer> ids);

    Question previewQuestion(Integer id);

    boolean editOrAdd(Question vo);

    IPage<AddQuestionPageRespVO> getAddPageList(AddQuestionPageReqVO vo);

    List<Question> selectQuestionList(Integer examId);
}
