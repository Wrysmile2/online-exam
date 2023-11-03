package com.xyh.controller.teacher;

import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.service.IndexService;
import com.xyh.vo.response.teacher.IndexVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@PreAuthorize("hasAnyRole('TEACHER')")
@RequestMapping("/teacher/dashboard/")
@RestController("teacherDashBoardController")
@Api(value = "教师首页的API", tags = "教师首页信息API")
public class DashBoardController {

   private IndexService indexService;

   @Autowired
    public DashBoardController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("index")
    @ApiOperation("教师首页信息的查询")
    public ResultAPI<IndexVO> getIndex(){
        IndexVO vo = indexService.getTeacherIndex();
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",vo);
    }
}
