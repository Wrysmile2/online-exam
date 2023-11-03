package com.xyh.vo.response.admin;

import com.xyh.pojo.Log;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("日志请求的响应")
public class LogRespVO extends Log {

    @ApiModelProperty("操作人账号")
    private String account;
}
