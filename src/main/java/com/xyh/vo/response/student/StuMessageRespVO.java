package com.xyh.vo.response.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 信息的返回的VO
 */

@Data
@ApiModel("响应公告信息的类")
public class StuMessageRespVO {
    @ApiModelProperty("公告编号")
    private Integer id;

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("是否已读")
    private Boolean readed;

    @ApiModelProperty("公告发布人")
    private String username;

    @ApiModelProperty("公告发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("公告内容")
    private  String messageContent;
}
