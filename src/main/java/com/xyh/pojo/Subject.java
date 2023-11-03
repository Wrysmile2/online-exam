package com.xyh.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName tbl_subject
 */
@Data
@ApiModel("科目实体类")
public class Subject implements Serializable {
    /**
     * 科目编号
     */
    @ApiModelProperty("科目主键编号")
    private Integer id;

    /**
     * 科目名称
     */
    @ApiModelProperty("科目名称")
    private String subjectName;

    /**
     * 排序优先级
     */
    @ApiModelProperty("序号")
    private String itemOrder;

    /**
     * 删除标志：0代表未删除，1代表删除
     */
    @ApiModelProperty("是否删除")
    private Boolean deleted;

   /* *//**
     * 年级号
     *//*
    private Integer levelGrade;

    *//**
     * 年级名称
     *//*
    private String levelName;*/

    private static final long serialVersionUID = 1L;

}