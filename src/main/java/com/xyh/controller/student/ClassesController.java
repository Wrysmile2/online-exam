package com.xyh.controller.student;

import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.pojo.Classes;
import com.xyh.service.ClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/student/classes/")
@RestController("StudentClassesController")
@Api(value = "学生更换班级", tags = "班级管理API")
public class ClassesController {

    private final ClassesService classesService;

    @Autowired
    public ClassesController(ClassesService classesService) {
        this.classesService = classesService;
    }

    @PostMapping("/list/{classesName}")
    @ApiOperation("根据班级名称进行班级搜索")
    public ResultAPI<List<Classes>> listClass(@PathVariable String classesName){

        List<Classes> list = classesService.selectByClassName(classesName);

        return new ResultAPI<>(ResponseConstant.SUCCESS,"",list);
    }
}
