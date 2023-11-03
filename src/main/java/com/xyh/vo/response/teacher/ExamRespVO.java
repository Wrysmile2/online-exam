package com.xyh.vo.response.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 试卷预览响应的VO
 */
@Data
@ApiModel("教师对试卷进行预览的响应类")
public class ExamRespVO {

    @ApiModelProperty("试卷编号")
    private Integer id;

    @ApiModelProperty("试卷名称")
    private String examName;

    @ApiModelProperty("试卷总分")
    private Double examTotal;

    @ApiModelProperty("开考时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("科目编号")
    private Integer subjectId;

    @ApiModelProperty("科目名称")
    private String subjectName;

    @ApiModelProperty("考试时间")
    private Integer examTime;

    @ApiModelProperty("考题项目")
    private List<QuestionItem> questionItems;

    /**
     * 所属班级
     */
    private List<Integer> classesIds;

}
