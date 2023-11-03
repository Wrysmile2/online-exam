package com.xyh.vo.response.teacher;

import com.xyh.vo.response.other.KeyValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("教师首页信息的响应类")
public class IndexVO {

    @ApiModelProperty("题目的数据")
    private List<KeyValue> quesData;

    @ApiModelProperty("试卷的数据")
    private List<KeyValue> examData;
}
