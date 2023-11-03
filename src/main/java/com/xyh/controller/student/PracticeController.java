package com.xyh.controller.student;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.pojo.User;
import com.xyh.service.QuestionPracticeService;
import com.xyh.vo.request.student.PracticeQuesReqVO;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.response.student.AnswerCardRespVO;
import com.xyh.vo.response.student.PracticeListRespVO;
import com.xyh.vo.response.student.PracticeQuestionRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/student/practice/")
@RestController("studentPracticeController")
@Api(value = "学生的题库练习", tags = "题库题目练习API")
public class PracticeController {

    private final QuestionPracticeService practiceService;
    private final WebContext webContext;

    @Autowired
    public PracticeController(QuestionPracticeService practiceService,WebContext webContext) {
        this.practiceService = practiceService;
        this.webContext = webContext;
    }

    @PostMapping("list")
    @ApiOperation("题库分页查询")
    public ResultAPI<IPage<PracticeListRespVO>> list(@RequestBody QueryReqVO vo){
        User user = webContext.getCurrentUser();

        vo.setUserId(user.getId());

        IPage<PracticeListRespVO> page = practiceService.selectPracticeList(vo);
        return new ResultAPI<>(200,"查询成功",page);
    }

    /**
     * 得到答题卡的初始数据
     * @param id
     * @return
     */
    @GetMapping("answerCard/{id}")
    @ApiOperation("初始化答题卡数据")
    public ResultAPI<List<AnswerCardRespVO>> answerCard(@PathVariable Integer id){
        Integer userId = webContext.getCurrentUser().getId();
        List<AnswerCardRespVO> list = practiceService.selectCard(id, userId);

        return new ResultAPI<>(200,"成功",list);
    }

    @PostMapping("question")
    @ApiOperation("查询题目")
    public ResultAPI<PracticeQuestionRespVO> search(@RequestBody PracticeQuesReqVO vo){
        Integer userId = webContext.getCurrentUser().getId();
        vo.setUserId(userId);

        PracticeQuestionRespVO respVO = practiceService.selectPracticeQuestion(vo);

        return new ResultAPI<>(200,"",respVO);
    }

    /**
     * 批改题目
     * @param vo
     * @return
     */
    @PostMapping("correct")
    @ApiOperation("提交题目进行批改")
    public ResultAPI<Boolean> correctQuestion(@RequestBody PracticeQuestionRespVO vo){

        Integer createUser = webContext.getCurrentUser().getId();

        Boolean flag = practiceService.correctQuestion(vo, createUser);

        return new ResultAPI<>(ResponseConstant.SUCCESS,"",flag);
    }

}
