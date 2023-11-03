package com.xyh.controller.admin;

import com.xyh.base.vo.ResultAPI;
import com.xyh.service.IndexService;
import com.xyh.vo.response.admin.AdminIndexRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/admin/dashboard/")
@RestController("adminDashBoardController")
@Api(value = "管理员首页数据展示", tags = "管理员首页数据API")
public class DashBoardController {

    private final IndexService indexService;

    @Autowired
    public DashBoardController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("index")
    @ApiOperation("查询管理员登录后的首页数据")
    public ResultAPI<AdminIndexRespVO> getIndex(){
        AdminIndexRespVO adminIndex = indexService.getAdminIndex();
        return new ResultAPI<>(200,"查询成功",adminIndex);
    }

}
