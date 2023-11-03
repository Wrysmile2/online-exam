package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.base.constants.QuestionConstant;
import com.xyh.config.cache.RedisCache;
import com.xyh.mapper.ExamMapper;
import com.xyh.mapper.ExamQuestionAnswerMapper;
import com.xyh.pojo.*;
import com.xyh.service.ExamRecordService;
import com.xyh.mapper.ExamRecordMapper;
import com.xyh.service.WrongService;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.request.teacher.ToMarkExamReqVO;
import com.xyh.vo.response.other.KeyValue;
import com.xyh.vo.response.student.ChartVO;
import com.xyh.vo.response.student.ExamViewAnswerItem;
import com.xyh.vo.response.student.ExamViewVO;
import com.xyh.vo.response.student.RecordRespVO;
import com.xyh.vo.response.teacher.ScoreDetailRespVO;
import com.xyh.vo.response.teacher.ToMarExamRecordRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
* @author 肖远华
* @description 针对表【tbl_exam_record】的数据库操作Service实现
* @createDate 2023-02-27 12:05:59
*/
@Service
public class ExamRecordServiceImpl extends ServiceImpl<ExamRecordMapper, ExamRecord>
    implements ExamRecordService{

    private ExamRecordMapper examRecordMapper;
    private ExamMapper examMapper;
    private ExamQuestionAnswerMapper examQuestionAnswerMapper;
    private WrongService wrongService;
    private RedisCache redisCache;

    @Autowired
    public ExamRecordServiceImpl(ExamRecordMapper examRecordMapper, ExamMapper examMapper,
                                 ExamQuestionAnswerMapper examQuestionAnswerMapper,
                                 WrongService wrongService,RedisCache redisCache) {
        this.examRecordMapper = examRecordMapper;
        this.examMapper = examMapper;
        this.examQuestionAnswerMapper = examQuestionAnswerMapper;
        this.wrongService = wrongService;
        this.redisCache = redisCache;
    }

    @Override
    public IPage<RecordRespVO> selectPageList(QueryReqVO vo) {
        IPage<RecordRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());

        IPage<RecordRespVO> pages = baseMapper.selectPageList(page, vo);
        return pages;
    }


    /**
     * 有缓存
     * 得到考试记录已经考过的试卷
     * @param examId
     * @param userId
     * @return
     */
    @Override
    public ExamViewVO selectExamView(Integer examId, Integer userId) {
        String key = "exam:" + examId + "-user:" + userId;
        ExamViewVO vo = new ExamViewVO();
        Object obj = redisCache.getCacheObject(key);
        if(!Objects.isNull(obj)){
            vo = (ExamViewVO) obj;
            return vo;
        }
        // 查询records中的数据
        LambdaQueryWrapper<ExamRecord> recordWrapper = new LambdaQueryWrapper<>();
        recordWrapper.eq(ExamRecord::getExamId,examId)
                    .eq(ExamRecord::getCreateUser,userId);
        ExamRecord record = examRecordMapper.selectOne(recordWrapper);
        vo.setExamId(record.getExamId());
        vo.setDoTime(record.getDoTime());
        vo.setTotalScore(record.getTotalScore());
        vo.setIscorrected(record.getIscorrected());

        //查询exam的数据
        Exam exam = examMapper.selectById(record.getExamId());
        vo.setExamName(exam.getExamName());
        vo.setExamTotal(exam.getExamTotal());

        //查询作答项
        List<ExamViewAnswerItem> answerItems = examQuestionAnswerMapper.selectExamViewList(userId, examId);
        vo.setAnswers(answerItems);

        //设置缓存
        redisCache.setCacheObject(key,vo,10, TimeUnit.MINUTES);

        return vo;
    }

    @Override
    public List<ScoreDetailRespVO> selectScoreList(Integer examId, Integer classesId) {
        AtomicInteger rank = new AtomicInteger(1);
        List<ScoreDetailRespVO> vos = baseMapper.selectScoreList(examId, classesId).stream().map(item -> {
            item.setRank(rank.getAndIncrement());
            return item;
        }).collect(Collectors.toList());
        return vos;
    }


    @Override
    public IPage<ToMarExamRecordRespVO> selectToMarkPageList(ToMarkExamReqVO vo) {
        IPage<ToMarExamRecordRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());
        return baseMapper.selectMarkPageList(page,vo);
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public ChartVO selectIndexChart(User user) {
        List<KeyValue> list = baseMapper.selectKVS(user);
        ChartVO vo = new ChartVO();
        List<String> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        list.forEach(item ->{
            xData.add(item.getName());
            yData.add(item.getValue());
        });
        vo.setXdata(xData);
        vo.setYdata(yData);
        return vo;
    }

    /**
     * 根据ID查询到待批改试卷的作答情况
     * @param id
     */
    @Override
    @Transactional(readOnly = true,rollbackFor = RuntimeException.class)
    public ExamViewVO selectRecordById(Integer id) {
        ExamViewVO vo = new ExamViewVO();
        //得到Record的数据
        ExamRecord record = baseMapper.selectById(id);
        //根据Record中examId得到试卷中的数据
        Exam exam = examMapper.selectById(record.getExamId());
        vo.setExamName(exam.getExamName());
        List<ExamViewAnswerItem> examViewAnswerItems = examQuestionAnswerMapper.selectExamViewList(record.getCreateUser(), exam.getId());
        vo.setAnswers(examViewAnswerItems);
        return vo;
    }

    /**
     * 教师对试卷进行批改
     * @param vo
     */
    @Override
    @Transactional
    public void correct(ExamViewVO vo) {
        //待批改试卷ID
        Integer id = vo.getExamId();
        //查出是哪一份答卷
        ExamRecord record = baseMapper.selectById(id);

        AtomicReference<Double> totalScore = new AtomicReference<Double>(0d);
        List<ExamViewAnswerItem> answers = vo.getAnswers();
        //重新计算总分
        answers.forEach(item -> totalScore.updateAndGet(v -> v + item.getScore()));
        // 设置待批改试卷的状态为批改状态
        record.setIscorrected(true);
        record.setTotalScore(totalScore.get());
        // 更新答卷状态，已经总分
        baseMapper.updateById(record);

        //准备批量更新状态,能被教师批改的就不存在试卷无简答题
        List<ExamQuestionAnswer> answerList = new ArrayList<>();
        answers.forEach(item ->{
            if(item.getQuestionType() == QuestionConstant.SHORT_QUESTION){
                ExamQuestionAnswer answer = new ExamQuestionAnswer();
                answer.setId(item.getId());
                answer.setScore(item.getScore());
                answer.setQuestionId(item.getQuestionId());
                answer.setAnswer(item.getAnswer());
                if(item.getScore() > 0){
                    answer.setCorrectError(true);
                }else{
                    answer.setCorrectError(false);
                }
                answerList.add(answer);
            }
        });
        baseMapper.updateBatch(answerList);

        //TODO
        Exam exam = examMapper.selectById(record.getExamId());
        List<Wrong> wrongList = new ArrayList<>();
        List<Wrong> updateWrongList = new ArrayList<>();

        answerList.forEach(item ->{
            if( !Objects.isNull(item.getCorrectError()) && !item.getCorrectError()){
                Wrong one = wrongService.selectByQuestionIdAndUserId(item.getQuestionId(), record.getCreateUser());
                Wrong wrong = getWrong(item,exam, record.getCreateUser());
                if(Objects.isNull(one)){
                    wrongList.add(wrong);
                }else{
                    wrong.setId(one.getId());
                    updateWrongList.add(wrong);
                }
            }
        });
        // 错题批量入库
        if(wrongList.size() > 0){
            wrongService.saveBatch(wrongList,10);
        }
        if(updateWrongList.size() > 0){
            wrongService.updateBatchById(updateWrongList,10);
        }
    }

    private Wrong getWrong(ExamQuestionAnswer questionAnswer,Exam exam,Integer createUser){
        Wrong wrong = new Wrong();
        wrong.setQuestionId(questionAnswer.getQuestionId());
        wrong.setSubjectId(exam.getSubjectId());
        wrong.setCreateUser(createUser);
        wrong.setAnswer(questionAnswer.getAnswer());
        return wrong;
    }
}




