package com.xyh.vo.response.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 试卷查看的VO
 */
@Data
@ApiModel("待批改试卷的响应")
public class ExamViewVO {

    @ApiModelProperty("试卷编号")
    private Integer examId;

    @ApiModelProperty("试卷名称")
    private String examName;

    @ApiModelProperty("做题时间")
    private Integer doTime;

    @ApiModelProperty("总得分")
    private Double totalScore;

    @ApiModelProperty("总分")
    private Double examTotal;

    @ApiModelProperty("是否被批改")
    private Boolean iscorrected;

    @ApiModelProperty("试卷作答的答案集")
    private List<ExamViewAnswerItem> answers;
}
