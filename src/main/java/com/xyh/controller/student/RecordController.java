package com.xyh.controller.student;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.service.ExamRecordService;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.response.student.ExamViewVO;
import com.xyh.vo.response.student.RecordRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/student/record/")
@RestController("studentRecordController")
@Api(value = "考试记录的API", tags = "考试记录的API")
public class RecordController {

    private final ExamRecordService examRecordService;
    private final WebContext webContext;

    @Autowired
    public RecordController(ExamRecordService examRecordService,WebContext webContext) {
        this.examRecordService = examRecordService;
        this.webContext = webContext;
    }

    @PostMapping("list")
    @ApiOperation("分页查询考试记录")
    public ResultAPI<IPage<RecordRespVO>> list(@RequestBody QueryReqVO vo){
        Integer userId = webContext.getCurrentUser().getId();
        vo.setUserId(userId);

        IPage<RecordRespVO> page = examRecordService.selectPageList(vo);
        return new ResultAPI(ResponseConstant.SUCCESS,"",page);
    }

    @GetMapping("select/{examId}")
    @ApiOperation("根据试卷编号查询考试记录")
    public ResultAPI<ExamViewVO> select(@PathVariable Integer examId){
        Integer userId = webContext.getCurrentUser().getId();
        ExamViewVO examViewVO = examRecordService.selectExamView(examId, userId);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",examViewVO);
    }
}
