package com.xyh.vo.response.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 学生端首页的信息的返回
 */
@Data
@ApiModel("考试试卷信息的返回类")
public class StuExamRespVO {

    @ApiModelProperty("试卷ID")
    private Integer id;

    @ApiModelProperty("试卷名称")
    private String examName;

    @ApiModelProperty("科目名称")
    private String subjectName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("开考时间")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("题目数")
    private Integer questionSum;

    @ApiModelProperty("总分")
    private Double examTotal;

    @ApiModelProperty("考试时长 单位：分钟")
    private Integer examTime;

    @ApiModelProperty("是否已经结束")
    private Integer isfinished;

    /**
     * 是否对某厂考试完成了
     * true 代表已经考过
     * false 代表还未进行考试
     */
    @ApiModelProperty("是否考过这场试 true：考过， false：未考")
    private Boolean completed;
}
