package com.xyh.vo.request.student;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用查询的VO
 */

@Data
@ApiModel("通用的请求类")
public class QueryReqVO extends PageVO {

    @ApiModelProperty("科目编号")
    private Integer subjectId;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("试卷名")
    private String examName;

}
