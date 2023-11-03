package com.xyh.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.pojo.Subject;
import com.xyh.service.SubjectService;
import com.xyh.vo.request.admin.SubjectReqVO;
import com.xyh.vo.response.admin.SubjectRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/admin/subject/")
@RestController("adminSubjectController")
@Api(value = "管理员对科目操作", tags = "科目管理API")
public class SubjectController {

    private final SubjectService subjectService;
    private final WebContext webContext;

    @Autowired
    public SubjectController(SubjectService subjectService,WebContext webContext) {
        this.subjectService = subjectService;
        this.webContext = webContext;
    }

    @PostMapping("/page/list")
    @ApiOperation("查询科目接口")
    public ResultAPI<IPage<SubjectRespVO>> getPageList(@RequestBody SubjectReqVO vo){
        IPage<SubjectRespVO> pageList = subjectService.getPageList(vo);
        return new ResultAPI<>(200,"查询成功",pageList);
    }

    @PostMapping("delByIds")
    @ApiOperation("删除科目接口")
    public ResultAPI<String> delByIds(@RequestBody Integer[] ids){
        if(ids == null || ids.length <= 0){
            return new ResultAPI<>(0,"删除失败","");
        }
        subjectService.delByIds(ids);
        return new ResultAPI<>(200,"删除成功","");
    }

    @GetMapping("select/{id}")
    @ApiOperation("查询科目接口")
    public ResultAPI<Subject> getSubjectById(@PathVariable Integer id){
        Subject subject = subjectService.getSubjectById(id);
        return new ResultAPI<>(0,"查询失败",subject);
    }

    @PostMapping("edit")
    @ApiOperation("编辑或新增科目接口")
    public ResultAPI<String> edit(@RequestBody Subject subject){
        boolean flag = subjectService.editSubject(subject);
        if(flag){
            return new ResultAPI<>(200,"操作成功","");
        }
        return new ResultAPI<>(0,"操作失败","");
    }
}
