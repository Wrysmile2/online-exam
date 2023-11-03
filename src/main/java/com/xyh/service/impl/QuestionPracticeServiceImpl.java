package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.mapper.QuestionMapper;
import com.xyh.mapper.WrongMapper;
import com.xyh.pojo.Question;
import com.xyh.pojo.QuestionPractice;
import com.xyh.pojo.Wrong;
import com.xyh.service.QuestionPracticeService;
import com.xyh.mapper.QuestionPracticeMapper;
import com.xyh.vo.request.student.PracticeQuesReqVO;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.response.student.AnswerCardRespVO;
import com.xyh.vo.response.student.PracticeListRespVO;
import com.xyh.vo.response.student.PracticeQuestionRespVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @author xyh
* @description 针对表【tbl_question_practice】的数据库操作Service实现
* @createDate 2022-12-27 22:22:44
*/
@Service
public class QuestionPracticeServiceImpl extends ServiceImpl<QuestionPracticeMapper, QuestionPractice>
implements QuestionPracticeService{

    private QuestionMapper questionMapper;
    private WrongMapper wrongMapper;

    @Autowired
    public QuestionPracticeServiceImpl(QuestionMapper questionMapper,WrongMapper wrongMapper) {
        this.questionMapper = questionMapper;
        this.wrongMapper = wrongMapper;
    }

    /**
     * 对练习列表首页的查询
     * @param vo
     * @return
     */
    @Override
    public IPage<PracticeListRespVO> selectPracticeList(QueryReqVO vo) {
        IPage<PracticeListRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());
        return baseMapper.selectPracticeList(page,vo);
    }

    @Override
    @Transactional
    public List<AnswerCardRespVO> selectCard(Integer subjectId, Integer userId) {
        //先得到答题卡应该有的数据(题目Id)
        List<Integer> idList = questionMapper.selectIdList(subjectId);

        // 得到用户答题卡中的数据
        AtomicInteger order = new AtomicInteger(1);
        List<AnswerCardRespVO> cardRespVOS = idList.stream().map(item -> {
            AnswerCardRespVO vo = new AnswerCardRespVO();
            vo.setQuestionId(item);
            Boolean flag = baseMapper.selectUserAnswerCard(item, userId);
            vo.setCorrect(flag);
            vo.setItemOrder(order.getAndIncrement());
            return vo;
        }).collect(Collectors.toList());

        return cardRespVOS;
    }

    /**
     * 查询练习列表的题目
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public PracticeQuestionRespVO selectPracticeQuestion(PracticeQuesReqVO vo) {
        Integer questionId = vo.getQuestionId();
        PracticeQuestionRespVO model = new PracticeQuestionRespVO();
        if(Objects.isNull(questionId)) {
            // 未做过的题目
            if (vo.getLastQuestion() == 0) {
                questionId = questionMapper.selectStartId(vo.getSubjectId());
            } else {   // 从上次做题的记录开始
                questionId = baseMapper.selectLastId(vo.getUserId(), vo.getSubjectId());
            }
        }
        Question question = questionMapper.selectById(questionId);
        BeanUtils.copyProperties(question,model);
        QuestionPractice practice = baseMapper.selectByQuestionId(questionId,vo.getUserId());
        if(Objects.isNull(practice)){
            model.setPracticeAnswer(null);
            model.setCorrect(null);
        }else{
            BeanUtils.copyProperties(practice,model);
        }
        model.setId(questionId);
        return model;
    }

    /**
     * 对练习的答案的批改
     * @param vo
     * @return
     */
    @Override
    @Transactional
    public Boolean correctQuestion(PracticeQuestionRespVO vo,Integer createUser) {
        QuestionPractice practice = new QuestionPractice();
        practice.setPracticeAnswer(vo.getPracticeAnswer());
        practice.setQuestionId(vo.getId());
        practice.setCreateUser(createUser);
        practice.setSubjectId(vo.getSubjectId());
        Boolean flag = isCorrect(vo);
        // 如果是错题，那么加入错题库中
        if(!flag){
            // 进行查询，看是否存在该数据
                Wrong wrong = wrongMapper.selectByQuestionIdAndCreateUser(practice.getQuestionId(), createUser);
                Wrong one = getWrong(practice,createUser);
            if(Objects.isNull(wrong)){
                wrongMapper.insert(one);
            }else{
                one.setId(wrong.getId());
                wrongMapper.updateById(one);
            }
        }
        practice.setCorrect(flag);
        baseMapper.insert(practice);
        return flag;
    }

    private Boolean isCorrect(PracticeQuestionRespVO vo){
        if(vo.getCorrectAnswer().equals(vo.getPracticeAnswer())){
            return true;
        }
        return false;
    }

    private Wrong getWrong(QuestionPractice practice,Integer createUser){
        Wrong wrong = new Wrong();
        wrong.setQuestionId(practice.getQuestionId());
        wrong.setSubjectId(practice.getSubjectId());
        wrong.setCreateUser(createUser);
        wrong.setAnswer(practice.getPracticeAnswer());
        return wrong;
    }




}
