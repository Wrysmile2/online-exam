package com.xyh.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 *  试卷表
 * @TableName tbl_exam
 */
@Data
public class Exam implements Serializable {
    /**
     * 试卷主键编号
     */
    private Integer id;

    /**
     * 试卷名称
     */
    private String examName;

    /**
     * 试卷总分
     */
    private Double examTotal;

    /**
     * 开考时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date endTime;

    /**
     * 考试时长，单位：min
     */
    private Integer examTime;

    /**
     * 所属科目编号
     */
    private Integer subjectId;

    /**
     * 创建人编号
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 是否删除标志：0代表未删除，1代表删除
     */
    private Boolean deleted;


    private static final long serialVersionUID = 1L;
}