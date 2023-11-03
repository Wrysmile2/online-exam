package com.xyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.pojo.ExamQuestionAnswer;
import com.xyh.service.ExamQuestionAnswerService;
import com.xyh.mapper.ExamQuestionAnswerMapper;
import org.springframework.stereotype.Service;

/**
* @author xyh
* @description 针对表【tbl_exam_question_answer】的数据库操作Service实现
* @createDate 2022-12-27 22:26:14
*/
@Service
public class ExamQuestionAnswerServiceImpl extends ServiceImpl<ExamQuestionAnswerMapper, ExamQuestionAnswer>
    implements ExamQuestionAnswerService{
}




