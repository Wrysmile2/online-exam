package com.xyh.vo.request.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 自动组卷的请求VO
 */
@Data
@ApiModel("自动组卷的请求")
public class AutoGroupReqVO {

    @ApiModelProperty(value = "开考时间" , required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "生成方式", required = true)
    private Integer generateType;

    @ApiModelProperty(value = "试卷总分", required = true)
    private Double examTotal;

    @ApiModelProperty(value = "科目编号", required = true)
    private Integer subjectId;

    @ApiModelProperty(value = "试卷名称", required = true)
    private String examName;

    @ApiModelProperty(value = "判断题个数", required = true)
    private Integer judgeNum;

    @ApiModelProperty(value = "每题分数", required = true)
    private Double judgeScore;

    @ApiModelProperty(value = "多选题个数",required = true)
    private Integer mutilNum;

    @ApiModelProperty(value = "多选题每题得分",required = true)
    private Double mutilScore;

    @ApiModelProperty(value = "单选题个数",required = true)
    private Integer singleNum;

    @ApiModelProperty(value = "单选题每题得分",required = true)
    private Double singleScore;

    @ApiModelProperty(value = "简答题个数",required = true)
    private Integer writtenNum;

    @ApiModelProperty(value = "简答题每题得分",required = true)
    private Double writtenScore;

    @ApiModelProperty(value = "需要考试的班级",required = true)
    private List<Integer> classesIds;
}
