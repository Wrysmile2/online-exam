package com.xyh.vo.request.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页基本类")
public class PageVO {

    @ApiModelProperty("分页页号， 默认 1 ")
    private Long pageIndex = 1L;

    @ApiModelProperty("分页条数， 默认 10 ")
    private Long pageSize = 10L;

}
