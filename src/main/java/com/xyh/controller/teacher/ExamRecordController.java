package com.xyh.controller.teacher;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.service.ExamRecordService;
import com.xyh.vo.request.teacher.ToMarkExamReqVO;
import com.xyh.vo.response.student.ExamViewVO;
import com.xyh.vo.response.teacher.ToMarExamRecordRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@PreAuthorize("hasAnyRole('TEACHER')")
@RequestMapping("/teacher/record/")
@RestController("teacherExamRecordController")
@Api(value = "教师对考卷进行管理", tags = "考卷管理API")
public class ExamRecordController {

    private final ExamRecordService examRecordService;


    @Autowired
    public ExamRecordController(ExamRecordService examRecordService) {
        this.examRecordService = examRecordService;
    }


    /**
     * 查看待批改的试卷列表
     * @param vo
     * @return
     */
    @PostMapping("list")
    @ApiOperation("查看待批改的试卷列表")
    public ResultAPI<IPage<ToMarExamRecordRespVO>> pageList(@RequestBody ToMarkExamReqVO vo){

        IPage<ToMarExamRecordRespVO> pages = examRecordService.selectToMarkPageList(vo);

        return new ResultAPI<>(ResponseConstant.SUCCESS,"",pages);
    }

    /**
     * 得到待批改的试卷
     * @param id
     * @return
     */
    @GetMapping("selectRecord/{id}")
    @ApiOperation("根据考卷编号得到待批改的试卷")
    public ResultAPI<ExamViewVO> selectRecord(@PathVariable Integer id){

        ExamViewVO viewVO = examRecordService.selectRecordById(id);

        return new ResultAPI<>(ResponseConstant.SUCCESS,"",viewVO);
    }

    @PostMapping("correct")
    @ApiOperation("批改试卷")
    public ResultAPI<String> correct(@RequestBody ExamViewVO vo){
        examRecordService.correct(vo);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"批改完成","");
    }

}
