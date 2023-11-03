package com.xyh.controller.teacher;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.pojo.Classes;
import com.xyh.service.ClassesService;
import com.xyh.vo.request.teacher.ClassesReqVO;
import com.xyh.vo.response.teacher.ClassesRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@PreAuthorize("hasAnyRole('TEACHER')")
@RequestMapping("/teacher/classes/")
@RestController("teacherClassesController")
@Api(value = "教师对班级的管理", tags = "班级管理API")
public class ClassesController {

    private final ClassesService classesService;

    private final WebContext webContext;

    @Autowired
    public ClassesController(ClassesService classesService,WebContext webContext) {
        this.classesService = classesService;
        this.webContext = webContext;
    }


    @PostMapping("list")
    @ApiOperation("班级分页的查询")
    public ResultAPI<IPage<ClassesRespVO>> list(@RequestBody ClassesReqVO vo){
        IPage<ClassesRespVO> page = classesService.selectPage(vo);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",page);
    }

    @GetMapping("select/{id}")
    @ApiOperation("根据编号查询班级")
    public ResultAPI<Classes> selectById(@PathVariable Integer id){
        Classes classes = classesService.getById(id);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",classes);
    }

    @PostMapping("edit")
    @ApiOperation("新增或者班级编辑")
    public ResultAPI<String> edit(@RequestBody Classes vo){
        Integer id = webContext.getCurrentUser().getId();
        classesService.edit(vo,id);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"操作成功","");
    }

    @PostMapping("dels")
    @ApiOperation("批量删除班级")
    public ResultAPI<String> delBatch(@RequestBody List<Integer> ids){
        classesService.delBatch(ids);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"删除成功","");
    }
}
