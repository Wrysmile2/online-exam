package com.xyh.vo.response.admin;

import lombok.Data;

/**
 * 用来统计访问量的VO
 */
@Data
public class VisitCountRespVO {

    private Integer registerCount;

    private Integer onlineCount;

    private Integer userCount;

    private Integer visitCount;
}
