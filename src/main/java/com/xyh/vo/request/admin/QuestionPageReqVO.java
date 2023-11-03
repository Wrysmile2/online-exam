package com.xyh.vo.request.admin;

import com.xyh.vo.request.other.PageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 教师对于题目查询的分页请求类
 */
@Data
@ApiModel("题目分页的请求类")
public class QuestionPageReqVO extends PageVO {
    @ApiModelProperty("题目名称")
    private String questionName;

    @ApiModelProperty("题目类型")
    private Integer questionType;

    @ApiModelProperty("科目编号")
    private List<Integer> subjectIds;

    @ApiModelProperty("是否删除")
    private Boolean deleted;

}
