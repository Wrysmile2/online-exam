package com.xyh.vo.response.student;

import com.xyh.pojo.Question;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单个错题项
 */
@Data
@ApiModel("错题项")
public class WrongDataItem {
    @ApiModelProperty("题目编号")
    private Integer id;

    @ApiModelProperty("参考答案")
    private String answer;

    @ApiModelProperty("题型")
    private Integer questionType;

    @ApiModelProperty("题干")
    private String questionName;

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

    @ApiModelProperty("题目分数")
    private Double questionScore;

    @ApiModelProperty("题目难度")
    private Integer difficulty;

    @ApiModelProperty("题目解析")
    private String questionDesc;
}
