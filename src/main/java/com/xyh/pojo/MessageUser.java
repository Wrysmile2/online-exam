package com.xyh.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName tbl_message_user
 */
@Data
public class MessageUser implements Serializable {
    /**
     * 主键编号
     */
    private Integer id;

    /**
     * 接收人编号
     */
    private Integer receiveId;

    /**
     * 发布人编号
     */
    private Integer createUser;

    /**
     * 是否已读标志 0 代表未读 1 代表已读
     */
    private Boolean readed;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 阅读时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date readTime;

    /**
     * 消息编号
     */
    private Integer messageId;

    private static final long serialVersionUID = 1L;
}