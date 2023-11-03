package com.xyh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * 试卷-题目联系表
 * @TableName tbl_question_exam_rel
 */
@Data
public class QuestionExamRel implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * 试卷编号
     */
    private Integer examId;

    /**
     * 题目编号
     */
    private Integer questionId;

    /**
     * 考试的分数
     */
    private Double questionScore;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}