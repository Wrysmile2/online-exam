package com.xyh.vo.request.student;

import lombok.Data;

import java.util.List;

/**
 * 学生提交的考试的答案数据
 */
@Data
public class ExamQuesReqVO {
    private Integer examId;
    private Integer doTime;

    private List<AnswerItem> answers;
}
