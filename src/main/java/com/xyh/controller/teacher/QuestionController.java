package com.xyh.controller.teacher;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.pojo.Question;
import com.xyh.service.QuestionService;
import com.xyh.vo.request.admin.AddQuestionPageReqVO;
import com.xyh.vo.response.admin.AddQuestionPageRespVO;
import com.xyh.vo.request.admin.QuestionPageReqVO;
import com.xyh.vo.response.admin.QuestionPageRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@PreAuthorize("hasAnyRole('TEACHER')")
@RequestMapping("/teacher/question/")
@RestController("teacherQuestionController")
@Api(value = "题目的管理",tags = "题目管理的API")
public class QuestionController {

    private final QuestionService questionService;
    private final WebContext webContext;

    @Autowired
    public QuestionController(QuestionService questionService,WebContext webContext) {
        this.questionService = questionService;
        this.webContext = webContext;
    }

    @PostMapping("/page/list")
    @ApiOperation("题目列表的分页")
    public ResultAPI<IPage<QuestionPageRespVO> > pageList(@RequestBody QuestionPageReqVO vo){
        IPage<QuestionPageRespVO> pages = questionService.getPageList(vo);
        return new ResultAPI<>(200,"查询成功",pages);
    }

    @PostMapping("delByIds")
    @ApiOperation("批量进行题目删除")
    public  ResultAPI<String> delByIds(@RequestBody List<Integer> ids){
        questionService.delByIds(ids);
        return new ResultAPI<>(200,"删除成功","");
    }

    /**
     * 题目预览
     * @param id
     * @return
     */
    @GetMapping("preview/{id}")
    @ApiOperation("根据题目编号进行题目预览")
    public ResultAPI<Question> preview(@PathVariable Integer id){
        Question question = questionService.previewQuestion(id);
        return new ResultAPI<>(200,"查询成功",question);
    }

    /**
     *
     * @param vo
     * @return
     */
    @PostMapping("edit")
    @ApiOperation("新增或者编辑题目")
    public ResultAPI<String> editOrAdd(@RequestBody Question vo){
        Integer createUser = webContext.getCurrentUser().getId();
        vo.setCreateUser(createUser);
        boolean flag = questionService.editOrAdd(vo);
        if(flag){
            return new ResultAPI<>(200,"操作成功","");
        }
        return new ResultAPI<>(0,"操作失败，请刷新重试","");
    }

    /**
     *
     * 根据id获取题目信息
     * @param id
     * @return
     */
    @GetMapping("getQuestionById/{id}")
    @ApiOperation("根据编号获取题目的详细信息")
    public ResultAPI<Question> getQuestionById(@PathVariable Integer id){
        Question question = questionService.previewQuestion(id);
        return new ResultAPI<>(200,"查询成功",question);
    }

    /**
     * 添加题目的分页
     * @param vo
     * @return
     */
    @PostMapping("add/page/list")
    @ApiOperation(value = "",hidden = true)
    public ResultAPI<IPage<AddQuestionPageRespVO>> addPageList(@RequestBody AddQuestionPageReqVO vo){
        IPage<AddQuestionPageRespVO> pageList = questionService.getAddPageList(vo);
        return new ResultAPI<>(200,"查询成功",pageList);
    }
}
