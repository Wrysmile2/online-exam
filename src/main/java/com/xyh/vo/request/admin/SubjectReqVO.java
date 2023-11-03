package com.xyh.vo.request.admin;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 对于学科的请求VO
 */
@Data
@ApiModel("科目的请求类")
public class SubjectReqVO extends PageVO {

    @ApiModelProperty(value = "科目名称", required = false)
    private String subjectName;
}
