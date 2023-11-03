package com.xyh.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.service.LogService;
import com.xyh.vo.request.admin.LogReqVO;
import com.xyh.vo.response.admin.LogRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/log/")
@RestController("logController")
@Api(value = "管理员权限可操作", tags = "日志查询API")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("page/list")
    @ApiOperation("日志查询接口")
    public ResultAPI<IPage<LogRespVO>> pageList(@RequestBody LogReqVO vo){
        IPage<LogRespVO> pageList = logService.getPageList(vo);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"查询成功",pageList);
    }
}
