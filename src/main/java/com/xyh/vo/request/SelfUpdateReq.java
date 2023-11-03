package com.xyh.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("个人信息修改的请求类")
public class SelfUpdateReq {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    /**
     * 性别 0代表男 1代表女
     */
    @ApiModelProperty(value = "性别 0：男 1：女",required = true)
    private Integer sex;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "用户邮箱",required = true)
    private String userEmail;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    private Date birthDay;

    /**
     * 所属班级
     */
    @ApiModelProperty(value = "班级,学生可选择",required = false)
    private Integer classesId;
}
