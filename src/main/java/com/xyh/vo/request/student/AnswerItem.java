package com.xyh.vo.request.student;

import lombok.Data;

import java.util.List;

/**
 * 学生考试提交的答案项
 */
@Data
public class AnswerItem {
    private String answer;
    private List<String> answerList;
    private Integer questionId;
}
