package com.xyh.vo.response.student;

import com.xyh.pojo.ExamRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于返回考试记录中的数据
 */
@Data
@ApiModel("考试记录的响应")
public class RecordRespVO extends ExamRecord {

    @ApiModelProperty("试卷名称")
    private String examName;

    @ApiModelProperty("科目名称")
    private String subjectName;
}
