package com.xyh.vo.response.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("答题卡的响应类")
public class AnswerCardRespVO {

    @ApiModelProperty("题目编号")
    private Integer questionId;

    @ApiModelProperty("是否正确")
    private Boolean correct;

    @ApiModelProperty("序号")
    private Integer itemOrder;
}
