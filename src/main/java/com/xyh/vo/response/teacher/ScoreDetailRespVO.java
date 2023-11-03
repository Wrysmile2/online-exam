package com.xyh.vo.response.teacher;

import lombok.Data;

/**
 * 某一次考试某个班级的详情数据
 */
@Data
public class ScoreDetailRespVO {

    /**
     * 排名
     */
    private Integer rank;

    private String account;

    private String username;

    private Double totalScore;

    private Boolean iscorrected;

}
