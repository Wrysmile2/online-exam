package com.xyh.controller.student;

import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.pojo.User;
import com.xyh.service.ExamRecordService;
import com.xyh.service.ExamService;
import com.xyh.vo.response.student.ChartVO;
import com.xyh.vo.response.student.StuExamRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/student/dashboard/")
@RestController("studentDashBoardController")
@Api(value = "学生首页信息查询", tags = "学生首页信息API")
public class DashBoardController {

    private final ExamService examService;

    private final ExamRecordService examRecordService;

    private final WebContext webContext;

    @Autowired
    public DashBoardController(ExamService examService, WebContext webContext,ExamRecordService examRecordService) {
        this.examService = examService;
        this.webContext = webContext;
        this.examRecordService = examRecordService;
    }


    /**
     * 得到学生界面首页的试卷
     * @return
     */
    @GetMapping("index")
    @ApiOperation("首页的试卷信息")
    public ResultAPI<List<StuExamRespVO>>index(){
        User user = webContext.getCurrentUser();
        Integer classesId = user.getClassesId();
        Integer userId = user.getId();

        List<StuExamRespVO> vos = examService.selectRecentExam(classesId,userId);

        return new ResultAPI<>(200,"查询成功",vos);
    }

    @GetMapping("chart")
    @ApiOperation("首页的考试成绩")
    public ResultAPI<ChartVO> chart(){
        User user = webContext.getCurrentUser();
        ChartVO chartVO = examRecordService.selectIndexChart(user);

        return new ResultAPI<>(ResponseConstant.SUCCESS,"",chartVO);
    }

}
