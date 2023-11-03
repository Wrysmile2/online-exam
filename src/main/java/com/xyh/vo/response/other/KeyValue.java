package com.xyh.vo.response.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员对于题目的数据
 */
@Data
@ApiModel("键值对")
public class KeyValue {

    @ApiModelProperty("值，int")
    private Integer value;

    @ApiModelProperty("名称")
    private String name;
}
