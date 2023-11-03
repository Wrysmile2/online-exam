package com.xyh.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 *  试卷题目作答表
 * @TableName tbl_exam_question_answer
 */
@Data
public class ExamQuestionAnswer implements Serializable {
    /**
     * 主键编号
     */
    private Integer id;

    /**
     * 作答答案
     */
    private String answer;

    /**
     * 得分
     */
    private Double score;

    /**
     * 是否正确：0代表错误 1代表正确
     */
    private Boolean correctError;

    /**
     * 试卷编号
     */
    private Integer examId;

    /**
     * 题目编号
     */
    private Integer questionId;

    /**
     * 作答者
     */
    private Integer createUser;

    private static final long serialVersionUID = 1L;
}