package com.xyh.vo.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("题目分页的响应")
public class QuestionPageRespVO {
    @ApiModelProperty("题目编号")
    private Integer id;

    @ApiModelProperty("题目名称")
    private String questionName;

    @ApiModelProperty("题目类型")
    private Integer questionType;

    @ApiModelProperty("题目分数")
    private Double questionScore;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("科目名称")
    private String subjectName;

    @ApiModelProperty("用户名")
    private String username;
}
