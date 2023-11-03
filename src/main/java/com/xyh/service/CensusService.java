package com.xyh.service;

import com.xyh.vo.response.admin.VisitCountRespVO;

import java.util.List;
import java.util.Map;

public interface CensusService {

    /**
     * 得到访问量的数据
     * @return
     */
    VisitCountRespVO getVisitData();

    Map<String,Object> getActiveData(List<String> times, List<Integer> diffDays);
}
