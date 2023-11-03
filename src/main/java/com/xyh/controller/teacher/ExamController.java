package com.xyh.controller.teacher;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.service.ExamRecordService;
import com.xyh.service.ExamService;
import com.xyh.service.QuestionService;
import com.xyh.vo.request.admin.ExamPageReqVO;
import com.xyh.vo.request.teacher.AutoGroupReqVO;
import com.xyh.vo.request.teacher.ScoreReqVO;
import com.xyh.vo.response.admin.ExamPageRespVO;
import com.xyh.vo.response.teacher.ExamRespVO;
import com.xyh.vo.response.teacher.ScoreDetailRespVO;
import com.xyh.vo.response.teacher.ScoreRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@PreAuthorize("hasAnyRole('TEACHER')")
@RequestMapping("/teacher/exam/")
@RestController("teacherExamController")
@Api(value = "教师对试卷的管理", tags = "试卷管理的API")
public class ExamController {

    private final ExamService examService;

    private final ExamRecordService examRecordService;

    private final WebContext webContext;

    @Autowired
    public ExamController(ExamService examService,WebContext webContext, ExamRecordService examRecordService) {
        this.examService = examService;
        this.webContext = webContext;
        this.examRecordService = examRecordService;
    }

    /**
     * 分页查询
     * @param vo
     * @return
     */
    @PostMapping("page/list")
    @ApiOperation("试卷分页查询")
    public ResultAPI<IPage<ExamPageRespVO>> pageList(@RequestBody ExamPageReqVO vo){
        IPage<ExamPageRespVO> pages = examService.pageList(vo);

        return new ResultAPI<>(200,"查询成功",pages);

    }

    /**
     * 根据Id查询试卷
     * @param id
     * @return
     */
    @GetMapping("getExamById/{id}")
    @ApiOperation("根据试卷编号进行试卷查询")
    public ResultAPI<ExamRespVO> getExamById(@PathVariable Integer id){

        ExamRespVO vo = examService.selectExamById(id);

        return new ResultAPI<>(200,"成功",vo);
    }

    /**
     * 手动组卷或者编辑试卷
     * @param vo
     * @return
     */
    @PostMapping("edit")
    @ApiOperation("手动组卷或者编辑试卷")
    public ResultAPI<Integer> editOrAdd(@RequestBody ExamRespVO vo){
        Integer createUser = webContext.getCurrentUser().getId();
        Integer examId = examService.editOrAdd(vo, createUser);
        return new ResultAPI<>(200,"操作成功",examId);
    }

    @GetMapping("delById/{id}")
    @ApiOperation("根据试卷编号删除试卷")
    public ResultAPI<String> delById(@PathVariable Integer id){
        boolean flag = examService.delById(id);
        if(flag){
            return new ResultAPI<>(200,"删除成功","");
        }
        return new ResultAPI<>(0,"请刷新重试","");
    }


    /**
     * 自动组件的VO
     * @param vo
     * @return
     */
    @PostMapping("autoGroup")
    @ApiOperation("系统自动组卷")
    public ResultAPI<String> autoGroup(@RequestBody AutoGroupReqVO vo){
        Integer userId = webContext.getCurrentUser().getId();
        String flag = examService.autoGroup(vo,userId);
        if(flag.equals("true")){
            return new ResultAPI<>(ResponseConstant.SUCCESS,"组卷成功","");
        }
        return new ResultAPI<>(ResponseConstant.FAILED,flag,"");
    }

    @PostMapping("scoreList")
    @ApiOperation("系统自动组卷")
    public ResultAPI<IPage<ScoreRespVO>> scoreList(@RequestBody ScoreReqVO vo){

        IPage<ScoreRespVO> pages = examService.selectScorePage(vo);

        return new ResultAPI<>(ResponseConstant.SUCCESS,"",pages);

    }

    @GetMapping("analysis/{examId}/{classesId}")
    @ApiOperation("查看班级考试信息")
    public ResultAPI<List<ScoreDetailRespVO>> scoreAnalysis(@PathVariable Integer examId ,@PathVariable Integer classesId){
        List<ScoreDetailRespVO> list = examRecordService.selectScoreList(examId, classesId);
        return new ResultAPI<>(ResponseConstant.SUCCESS,"",list);
    }




}
