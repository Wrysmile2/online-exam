package com.xyh.vo.request.admin;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户查询请求的VO
 */

@Data
@ApiModel("用户信息请求类")
public class UserReqVO extends PageVO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("角色权限 0代表管理员，1代表老师，2代表学生用户")
    private Integer role;

    @ApiModelProperty("用户状态 0代表正常 1代表封禁")
    private Integer status;
}
