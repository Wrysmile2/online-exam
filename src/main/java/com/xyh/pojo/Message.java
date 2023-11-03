package com.xyh.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName tbl_message
 */
@Data
public class Message implements Serializable {
    /**
     * 主键编号
     */
    private Integer id;

    /**
     * 主题
     */
    private String title;

    /**
     * 公告内容
     */
    private String messageContent;

    /**
     * 应收人数
     */
    private Integer receiveNum;

    /**
     * 实际接收人数/已读人数
     */
    private Integer actualReceiveNum;

    /**
     * 公告发布人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private static final long serialVersionUID = 1L;
}