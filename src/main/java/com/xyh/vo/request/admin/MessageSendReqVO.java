package com.xyh.vo.request.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 公告发送的类
 */
@Data
@ApiModel("发送公告的请求类")
public class MessageSendReqVO {

    @ApiModelProperty("公告标题")
    private String title;

    @ApiModelProperty("公告内容")
    private String  messageContent;

    // 接收者的编号
    @ApiModelProperty("接受者的编号")
    private List<Integer> receiveUserIds;

}
