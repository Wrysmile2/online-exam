package com.xyh.vo.response.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 错题本首页的VO
 */
@Data
@ApiModel("错题首页响应类")
public class WrongIndexVO {
    @ApiModelProperty("科目编号")
    private Integer id;

    @ApiModelProperty("科目名称")
    private String subjectName;

    @ApiModelProperty("错题数")
    private Integer wrongCount;
}
