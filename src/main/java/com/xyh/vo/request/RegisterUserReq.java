package com.xyh.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户注册请求类")
@Data
public class RegisterUserReq {

    @ApiModelProperty(value = "注册账号" ,required = true)
    private String account;

    @ApiModelProperty(value = "注册密码", required = true)
    private String password;

    @ApiModelProperty(value = "注册用户名", required = true)
    private String username;

    @ApiModelProperty(value = "绑定邮箱", required = true)
    private String userEmail;

}
