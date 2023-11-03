package com.xyh.vo.request.admin;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 试卷的分页的
 */
@Data
@ApiModel("教师对试卷分页的请求类")
public class ExamPageReqVO extends PageVO {

    @ApiModelProperty("是否删除")
    private Boolean deleted;

    @ApiModelProperty("科目编号")
    private Integer subjectId;

    @ApiModelProperty("试卷名称")
    private String examName;
}
