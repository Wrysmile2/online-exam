package com.xyh.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName tbl_wrong
 */
@Data
public class Wrong implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 题目ID
     */
    private Integer questionId;

    /**
     * 错误答案
     */
    private String answer;

    /**
     * 
     */
    private Integer createUser;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 科目Id
     */
    private Integer subjectId;

    private static final long serialVersionUID = 1L;
}