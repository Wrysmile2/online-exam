package com.xyh.vo.response.teacher;

import com.xyh.pojo.Classes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("教师查询班级的响应类")
public class ClassesRespVO extends Classes {

    @ApiModelProperty("用户名")
    private String username;
}
