package com.xyh.vo.request.teacher;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("教师对班级的请求类")
public class ClassesReqVO extends PageVO {

    @ApiModelProperty("班级名称")
    private String classesName;
}
