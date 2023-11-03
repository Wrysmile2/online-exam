package com.xyh.vo.request.admin;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 日志相关的
 */
@Data
@ApiModel("日志查询的请求")
public class LogReqVO extends PageVO {

    @ApiModelProperty(value = "创建人编号", required = false)
    private Integer createUser;

    @ApiModelProperty(value = "创建人名称", required = false)
    private String username;
}
