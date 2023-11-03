package com.xyh.vo.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 对于教师端的试卷分页的响应数据
 */
@Data
@ApiModel("教师试卷分页信息的响应")
public class ExamPageRespVO {

    @ApiModelProperty("试卷编号")
    private Integer id;

    @ApiModelProperty("试卷名称")
    private String examName;

    @ApiModelProperty("科目名称")
    private String subjectName;

//    private String levelName;

    @ApiModelProperty("班级名称")
    private String classesName;

    @ApiModelProperty("总分")
    private Double examTotal;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("开考时间")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("创建人")
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date createTime;

}
