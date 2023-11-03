package com.xyh.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.pojo.Wrong;
import com.xyh.service.WrongService;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.response.student.WrongIndexVO;
import com.xyh.vo.response.student.WrongRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/student/wrong/")
@RestController("studentWrongController")
@Api(value = "错题管理", tags = "错题管理的API")
public class WrongController {

    private final WrongService wrongService;
    private final WebContext webContext;

    @Autowired
    public WrongController(WrongService wrongService,WebContext webContext) {
        this.wrongService = wrongService;
        this.webContext = webContext;
    }

    @PostMapping("list")
    @ApiOperation("错题首页分页的查询")
    public ResultAPI<IPage<WrongIndexVO>> list(@RequestBody QueryReqVO vo){
        Integer userId = webContext.getCurrentUser().getId();
        vo.setUserId(userId);

        IPage<WrongIndexVO> page = wrongService.pageList(vo);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",page);
    }

    @PostMapping("wrongList")
    @ApiOperation("查询错题信信息")
    public ResultAPI<WrongRespVO> wrongList(@RequestBody QueryReqVO vo){
        Integer userId = webContext.getCurrentUser().getId();
        vo.setUserId(userId);

        WrongRespVO wrongRespVO = wrongService.selectWrongsByPage(vo);

        return new ResultAPI<>(ResponseConstant.SUCCESS,"",wrongRespVO);
    }

    @GetMapping("/remove/{id}")
    @ApiOperation("移除错题")
    public ResultAPI<Boolean>removeWrong(@PathVariable Integer id){
        Integer userId = webContext.getCurrentUser().getId();

        LambdaQueryWrapper<Wrong> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Wrong::getCreateUser,userId)
                .eq(Wrong::getId,id);
        Boolean flag = wrongService.remove(wrapper);

        return new ResultAPI<>(ResponseConstant.SUCCESS,"移除成功",flag);
    }



}
