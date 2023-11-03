package com.xyh.controller.student;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.pojo.Question;
import com.xyh.pojo.User;
import com.xyh.service.ExamService;
import com.xyh.service.QuestionService;
import com.xyh.vo.request.student.ExamQueryReqVO;
import com.xyh.vo.request.student.ExamQuesReqVO;
import com.xyh.vo.response.student.StuExamRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/student/exam/")
@RestController("studentExamController")
@Api(value = "学生的考试管理", tags = "考试管理API")
public class ExamController {

    private final ExamService examService;
    private final QuestionService questionService;
    private final WebContext webContext;

    @Autowired
    public ExamController(ExamService examService,QuestionService questionService,WebContext webContext) {
        this.examService = examService;
        this.questionService = questionService;
        this.webContext = webContext;
    }

    @PostMapping("/list")
    @ApiOperation("考试试卷的查询")
    public ResultAPI<IPage<StuExamRespVO>> getStudentExam(@RequestBody ExamQueryReqVO vo){
        User user = webContext.getCurrentUser();
        Integer classesId = user.getClassesId();
        Integer userId = user.getId();
        vo.setClassesId(classesId);
        IPage<StuExamRespVO> page = examService.selectStudentExam(vo,userId);
        return  new ResultAPI<>(200,"查询成功",page);
    }

    /**
     * 得到试卷上的题目合集
     * @param examId
     * @return
     */
    @GetMapping("/question/list/{examId}")
    @ApiOperation("根据试卷ID得到试卷题目的合集")
    public ResultAPI<List<Question>> getQuestionList(@PathVariable Integer examId){
        List<Question> questions = questionService.selectQuestionList(examId);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",questions);
    }


    /**
     * 自动批改试卷（客观题）
     * @param vo
     * @return 系统得分
     */
    @PostMapping("correct")
    @ApiOperation("系统评分")
    public ResultAPI<String> correctExam(@RequestBody ExamQuesReqVO vo){
        Integer userId = webContext.getCurrentUser().getId();

        Double score = examService.correctExam(vo, userId);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",String.valueOf(score));
    }

}
