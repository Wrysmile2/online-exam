package com.xyh.vo.request.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("题目练习的请求类")
public class PracticeQuesReqVO {

    @ApiModelProperty("科目编号")
    private Integer subjectId;

    @ApiModelProperty("判断是否进行过做题")
    private Integer lastQuestion;

    @ApiModelProperty("题目的编号")
    private Integer questionId;
    private Integer userId;
}
