package com.xyh.vo.response.teacher;

import lombok.Data;

@Data
public class ScoreRespVO {

    private Integer classesId;

    private Integer examId;

    private String examName;

    private String classesName;

    private Integer classesNum;

    private Integer actualNum;

}
