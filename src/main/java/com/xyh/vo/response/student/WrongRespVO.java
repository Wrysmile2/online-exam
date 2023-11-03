package com.xyh.vo.response.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 错题本阅读页进行返回的VO
 */
@Data
@ApiModel("错题响应类")
public class WrongRespVO {

    @ApiModelProperty("科目名称")
    private String subjectName;

    @ApiModelProperty("错题数据")
    private List<WrongDataItem> wrongs;
}
