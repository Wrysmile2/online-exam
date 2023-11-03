package com.xyh.vo.response.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("练习册题目的请求类")
public class PracticeQuestionRespVO {

    // 题目的id
    @ApiModelProperty("题目编号")
    private Integer id;

    @ApiModelProperty("题目名称")
    private String questionName;

    @ApiModelProperty("题目类型")
    private Integer questionType;

    @ApiModelProperty("答案A")
    private String answerA;

    @ApiModelProperty("答案B")
    private String answerB;

    @ApiModelProperty("答案C")
    private String answerC;

    @ApiModelProperty("答案D")
    private String answerD;

    @ApiModelProperty("参考答案")
    private String correctAnswer;

    @ApiModelProperty("题目描述")
    private String questionDesc;

    @ApiModelProperty("作答答案")
    private String practiceAnswer;

    @ApiModelProperty("是否正确")
    private Boolean correct;

    @ApiModelProperty("科目编号")
    private Integer subjectId;

}
