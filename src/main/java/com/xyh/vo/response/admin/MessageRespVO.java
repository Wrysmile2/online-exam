package com.xyh.vo.response.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 公告列表的响应类
 */

@Data
@ApiModel("公告的响应类")
public class MessageRespVO {

    @ApiModelProperty("公告编号")
    private Integer id;

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("公告内容")
    private String messageContent;

    @ApiModelProperty("发送人")
    private String sendUserName;

    @ApiModelProperty("应收人数")
    private Integer receiveNum;

    @ApiModelProperty("已读人数")
    private String actualReceiveNum;

    @ApiModelProperty("公告发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
