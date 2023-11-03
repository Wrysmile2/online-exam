package com.xyh.vo.request.teacher;

import com.xyh.vo.request.other.PageVO;
import lombok.Data;

@Data
public class ScoreReqVO extends PageVO {

    private String examName;

    private Integer classesId;
}
