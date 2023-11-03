package com.xyh.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  题目表
 * @TableName tbl_question
 */
@Data
@ApiModel("题目的实体类")
public class Question implements Serializable {
    /**
     * 主键编号
     */
    @ApiModelProperty("主键id")
    private Integer id;

    /**
     * 题目描述
     */
    @ApiModelProperty("题干")
    private String questionName;

    /**
     * 题目类别：1单选题、2多选题、3判断题、4简答题
     */
    @ApiModelProperty("题型：1单选题、2多选题、3判断题、4简答题")
    private Integer questionType;

    /**
     * 题目分数
     */
    @ApiModelProperty("题目分数")
    private Double questionScore;

    /**
     * 答案A
     */
    @ApiModelProperty("A")
    private String answerA;

    /**
     *
     */
    @ApiModelProperty("B")
    private String answerB;

    /**
     *
     */
    @ApiModelProperty("C")
    private String answerC;

    /**
     *
     */
    @ApiModelProperty("D")
    private String answerD;

    /**
     * 难度
     */
    @ApiModelProperty("难度")
    private Double difficulty;

    /**
     * 正确答案
     */
    @ApiModelProperty("参考答案")
    private String correctAnswer;

    /**
     * 题目解析
     */
    @ApiModelProperty("题解")
    private String questionDesc;

    /**
     * 所属科目编号
     */
    @ApiModelProperty("所属科目编号")
    private Integer subjectId;

    /**
     * 创建人编号
     */
    @ApiModelProperty("创建人编号")
    private Integer createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 是否删除0代表未删除，1代表删除
     */
    @ApiModelProperty("是否删除")
    private Boolean deleted;

    private static final long serialVersionUID = 1L;
}