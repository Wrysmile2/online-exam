package com.xyh.vo.response.teacher;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 对于教师需要进行试卷批改的回复VO
 */
@Data
@ApiModel("待批改试卷的响应类")
public class ToMarExamRecordRespVO {

    @ApiModelProperty("试卷编号")
    private Integer id;

    @ApiModelProperty("试卷名称")
    private String examName;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("班级名称")
    private String classesName;


    @ApiModelProperty("考试时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
