package com.xyh.vo.response.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 练习册首页的数据返回VO
 */
@Data
@ApiModel("练习册首页返回数据的VO")
public class PracticeListRespVO {

    @ApiModelProperty("题目编号")
    private Integer id;

    @ApiModelProperty("科目名称")
    private String subjectName;

    @ApiModelProperty("上次做题时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("题目总数")
    private Integer questionCount;

    @ApiModelProperty("题目完成数")
    private Integer finishedCount;

}
