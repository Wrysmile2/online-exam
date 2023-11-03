package com.xyh.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName tbl_user
 */
@Data
@ApiModel("用户信息类")
public class User implements Serializable {
    /**
     * 主键id
     */
    @ApiModelProperty("主键ID")
    private Integer id;

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String account;

    /**
     * 登录密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 性别 0代表男 1代表女
     */
    @ApiModelProperty("性别 0：男 1：女")
    private Integer sex;

    /**
     * 邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String userEmail;

    /**
     * 权限编号：0代表管理员，1代表老师，2代表学生用户
     */
    @ApiModelProperty("角色权限 0：管理员 1：老师 2：学生")
    private Integer role;

    /**
     * 用户状态：0代表正常 1代表封禁
     */
    @ApiModelProperty("账号状态： 0 正常 1 封禁")
    private Integer userStatus;

    /**
     * 生日
     */
    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8")
    private Date birthDay;

    /**
     * 头像路径
     */
    @ApiModelProperty("头像")
    private String imagePath;

    /**
     * 是否被删除，0代表未删除1代表已删除
     */
    @ApiModelProperty("是否删除 0：未删除 1：删除")
    private Boolean deleted;

    /**
     * 所属年级
     */
//    private Integer userLevel;

    /**
     * 所属班级
     */
    @ApiModelProperty("班级")
    private Integer classesId;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人编号")
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private static final long serialVersionUID = 1L;


}