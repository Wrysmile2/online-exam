package com.xyh.controller.admin;

import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.service.CensusService;
import com.xyh.utils.DateUtils;
import com.xyh.vo.response.admin.VisitCountRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@PreAuthorize("hasRole('ADMIN')")
@RestController("CensusController")
@RequestMapping("/admin/census/")
@Api(value = "用于管理员的数据统计", tags = "管理员数据统计API")
public class CensusController {

    private final CensusService censusService;


    @Autowired
    public CensusController(CensusService censusService) {
        this.censusService = censusService;
    }

    /**
     * 得到月访问量的数据，注册数等数据
     * @return
     */
    @GetMapping("visit")
    @ApiOperation("查看月访问量、注册数据接口")
    public ResultAPI<VisitCountRespVO> getVisitCount(){
        VisitCountRespVO visitData = censusService.getVisitData();

        return new ResultAPI<>(ResponseConstant.SUCCESS,"",visitData);
    }

    /**
     * 得到月份的活跃度
     * @param times
     * @return
     */
    @PostMapping("activeData")
    @ApiOperation("月活跃数据接口")
    public ResultAPI<Map<String,Object>> getActiveData(@RequestBody List<String> times ){
        Map<String, Object> map = censusService.getActiveData(times, DateUtils.getDiffDays(times.get(0), times.get(1)));
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",map);
    }

}
