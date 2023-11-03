package com.xyh.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录的请求类")
public class LoginUserReq {
    @ApiModelProperty(value = "登录账号", required = true)
    private String account;

    @ApiModelProperty(value = "登录密码",required = true)
    private String password;

    @ApiModelProperty(value = "用户登录权限，0：管理员，1：教师，2：学生",required = true)
    private Integer role;

//    @ApiModelProperty("验证码")
//    private String checkCode;
}
