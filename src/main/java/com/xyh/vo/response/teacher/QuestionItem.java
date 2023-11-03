package com.xyh.vo.response.teacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 一个题目项
 */

@Data
@ApiModel("考题项")
public class QuestionItem {

    @ApiModelProperty("题目编号")
    private Integer id;

    @ApiModelProperty("题目名称")
    private String questionName;

    @ApiModelProperty("答案A")
    private String answerA;

    @ApiModelProperty("答案B")
    private String answerB;

    @ApiModelProperty("答案C")
    private String answerC;

    @ApiModelProperty("答案D")
    private String answerD;

    @ApiModelProperty("题目类型")
    private Integer questionType;

    @ApiModelProperty("题目分数")
    private Double questionScore;
}
