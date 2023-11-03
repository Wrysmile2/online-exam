package com.xyh.controller.student;

import com.xyh.base.vo.ResultAPI;
import com.xyh.service.SubjectService;
import com.xyh.vo.response.other.KeyValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/student/subject/")
@RestController("/studentSubjectController")
@Api(value = "学生对科目的管理", tags = "科目管理API")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/list")
    @ApiOperation("分页查询科目")
    public ResultAPI<List<KeyValue>> getSubjects(){
        List<KeyValue> list = subjectService.selectSubjects();
        return new ResultAPI<>(200,"请求成功",list);
    }

}
