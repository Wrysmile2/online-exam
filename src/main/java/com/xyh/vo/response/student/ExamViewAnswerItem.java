package com.xyh.vo.response.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 试卷查看的VO
 */
@Data
@ApiModel("试卷答案集响应")
public class ExamViewAnswerItem {

    @ApiModelProperty("试卷编号")
    private Integer id;

    @ApiModelProperty("题目编号")
    private Integer questionId;

    @ApiModelProperty("得分")
    private Double score;

    @ApiModelProperty("是否正确")
    private Boolean correctError;

    @ApiModelProperty("题干")
    private String questionName;

    @ApiModelProperty("题型")
    private Integer questionType;

    @ApiModelProperty("题目分数")
    private Double questionScore;

    @ApiModelProperty("A")
    private String answerA;

    @ApiModelProperty("B")
    private String answerB;

    @ApiModelProperty("C")
    private String answerC;

    @ApiModelProperty("D")
    private String answerD;

    @ApiModelProperty("作答答案")
    private String correctAnswer;

    @ApiModelProperty("参考答案")
    private String answer;

    @ApiModelProperty("题目藐视")
    private String questionDesc;
}
