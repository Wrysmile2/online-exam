package com.xyh.controller.teacher;

import com.xyh.base.vo.ResultAPI;
import com.xyh.service.SubjectService;
import com.xyh.vo.response.other.KeyValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@PreAuthorize("hasAnyRole('TEACHER')")
@RequestMapping("teacher/subject")
@RestController("teacherSubjectController")
@Api(value = "教师对科目的管理", tags = "科目管理API")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("getSubjectName/{subjectName}")
    @ApiOperation("根据科目名称查询科目")
    public ResultAPI<List<KeyValue>> getSubjectList(@PathVariable String subjectName){
        List<KeyValue> list = subjectService.getSubjectName(subjectName);
        return new ResultAPI<>(200,"查询成功",list);
    }

}
