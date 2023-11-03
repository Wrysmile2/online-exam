package com.xyh.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName tbl_exam_record
 */
@Data
@ApiModel("考试记录实体类")
public class ExamRecord implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("主键ID")
    private Integer id;

    /**
     * 试卷id
     */
    @ApiModelProperty("试卷ID")
    private Integer examId;

    /**
     * 总分
     */
    @ApiModelProperty("试卷总分")
    private Double totalScore;

    /**
     * 用时，单位：秒
     */
    @ApiModelProperty("考试用时，单位：秒")
    private Integer doTime;

    /**
     *
     */
    @ApiModelProperty("考试人ID")
    private Integer createUser;

    /**
     *
     */
    @ApiModelProperty("交卷时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 是否被批改：0：未批改 1：已批改
     */
    @ApiModelProperty("是否批改")
    private Boolean iscorrected;

    private static final long serialVersionUID = 1L;
}