package com.xyh.vo.request.student;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学生界面考试的请求参数
 */
@Data
@ApiModel("学生试卷考试的请求参数")
public class ExamQueryReqVO extends PageVO {

    // 用户的班级
    @ApiModelProperty("班级编号")
    private Integer classesId;

    // 试卷是否已经完结
    @ApiModelProperty("是否已经结束")
    private Integer isfinished;

    //条件的学科的ID
    @ApiModelProperty("科目编号")
    private Integer subjectId;
}
