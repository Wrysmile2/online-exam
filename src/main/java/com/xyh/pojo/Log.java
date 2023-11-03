package com.xyh.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @TableName tbl_log
 */
@Data
@NoArgsConstructor
@ApiModel("日志类")
public class Log implements Serializable {
    /**
     * 日志主键编号
     */
    @ApiModelProperty("主键ID")
    private Integer id;

    /**
     * 日志内容
     */
    @ApiModelProperty("日志内容")
    private String logContent;


    /**
     * 日志操作人
     */
    @ApiModelProperty("日志操作人名称")
    private String username;

    /**
     * 操作人编号
     */
    @ApiModelProperty("日志操作人编号")
    private Integer createUser;

    /**
     * 是否是登录
     */
    @ApiModelProperty("是否为登录（用于统计活跃度）")
    private Boolean isLogin;

    /**
     * 日志创建时间
     */
    @ApiModelProperty("日志操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Log(String logContent,String username,  Integer createUser, Boolean isLogin, Date createTime) {
        this.logContent = logContent;
        this.username = username;
        this.createUser = createUser;
        this.isLogin = isLogin;
        this.createTime = createTime;
    }
}