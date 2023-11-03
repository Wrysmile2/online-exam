package com.xyh.vo.request.admin;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 消息列表分页查询的VO
 */
@Data
@ApiModel("公告的请求类")
public class MessageReqVO extends PageVO {
    @ApiModelProperty(value = "发送者的姓名", required = false)
    private String sendUserName;
}
