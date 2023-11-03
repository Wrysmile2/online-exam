package com.xyh.vo.response.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 科目的响应类
 */
@Data
public class SubjectRespVO {
    /**
     * 科目编号
     */
    @ApiModelProperty("科目主键编号")
    private Integer id;

    /**
     * 科目名称
     */
    @ApiModelProperty("科目名称")
    private String subjectName;

    /**
     * 排序优先级
     */
    @ApiModelProperty("序号")
    private String itemOrder;

}
