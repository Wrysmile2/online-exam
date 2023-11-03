package com.xyh.vo.request.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 密码重置的VO
 */
@Data
@ApiModel("重置密码的请求")
public class ResetPassReqVO {

    @ApiModelProperty(value = "账号", required = true)
    private String account;

    @ApiModelProperty(value = "邮箱",required = true)
    private String userEmail;

    @ApiModelProperty(value = "验证码",required = true)
    private String vailCode;

}
