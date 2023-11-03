package com.xyh.vo.request.teacher;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 教师查询待批改的试卷VO请求
 */
@Data
@ApiModel("查询待批改试卷的请求类")
public class ToMarkExamReqVO extends PageVO {
    @ApiModelProperty("试卷名称")
    private String examName;

    @ApiModelProperty("班级编号")
    private String classesId;

}
