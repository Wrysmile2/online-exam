package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.base.constants.QuestionConstant;
import com.xyh.config.cache.RedisCache;
import com.xyh.mapper.*;
import com.xyh.pojo.*;
import com.xyh.service.ExamQuestionAnswerService;
import com.xyh.service.ExamService;
import com.xyh.service.QuestionExamRelService;
import com.xyh.service.WrongService;
import com.xyh.utils.DateUtils;
import com.xyh.vo.request.admin.ExamPageReqVO;
import com.xyh.vo.request.student.AnswerItem;
import com.xyh.vo.request.student.ExamQueryReqVO;
import com.xyh.vo.request.student.ExamQuesReqVO;
import com.xyh.vo.request.teacher.AutoGroupReqVO;
import com.xyh.vo.request.teacher.CountVO;
import com.xyh.vo.request.teacher.ScoreReqVO;
import com.xyh.vo.response.admin.ExamPageRespVO;
import com.xyh.vo.response.student.StuExamRespVO;
import com.xyh.vo.response.teacher.AutoKeyValue;
import com.xyh.vo.response.teacher.ExamRespVO;
import com.xyh.vo.response.teacher.QuestionItem;
import com.xyh.vo.response.teacher.ScoreRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
* @author xyh
* @description 针对表【tbl_exam】的数据库操作Service实现
* @createDate 2022-12-27 22:26:14
*/
@Slf4j
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam>
    implements ExamService{

    private QuestionMapper questionMapper;

    private ExamQuestionAnswerService questionAnswerService;

    private QuestionExamRelService questionExamRelService;

    private ExamRecordMapper examRecordMapper;

    private ExamMapper examMapper;

    private WrongService wrongService;

    private ExamClassesRelMapper examClassesRelMapper;

    private ClassesMapper classesMapper;

    private RedisCache redisCache;

    @Autowired
    public ExamServiceImpl(QuestionMapper questionMapper, ExamQuestionAnswerService questionAnswerService,
                           QuestionExamRelService questionExamRelService, ExamRecordMapper examRecordMapper,
                           ExamMapper examMapper, WrongService wrongService,ExamClassesRelMapper examClassesRelMapper,
                           ClassesMapper classesMapper,RedisCache redisCache) {
        this.questionMapper = questionMapper;
        this.questionAnswerService = questionAnswerService;
        this.questionExamRelService = questionExamRelService;
        this.examRecordMapper = examRecordMapper;
        this.examMapper = examMapper;
        this.wrongService = wrongService;
        this.examClassesRelMapper = examClassesRelMapper;
        this.classesMapper = classesMapper;
        this.redisCache = redisCache;
    }

    @Override
    public IPage<ExamPageRespVO> pageList(ExamPageReqVO vo) {

        IPage<ExamPageRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());

        IPage<ExamPageRespVO> pageList = baseMapper.getPageList(page, vo);

        if(pageList.getPages() < vo.getPageIndex()){
            page = new Page<>(pageList.getPages(),vo.getPageSize());
            pageList = baseMapper.getPageList(page,vo);
        }
        List<ExamPageRespVO> vos = pageList.getRecords().stream().map(item -> {
            Integer examId = item.getId();
            List<String> list = classesMapper.selectByExamId(examId);
            item.setClassesName(list.stream().collect(Collectors.joining("、")));
            return item;
        }).collect(Collectors.toList());
        pageList.setRecords(vos);
        return pageList;
    }

    @Override
    @Transactional
    public ExamRespVO selectExamById(Integer id) {
        String key = "exam:"+id;
        ExamRespVO vo = null;
        Object obj = redisCache.getCacheObject(key);
        if(!Objects.isNull(obj)){
            vo = (ExamRespVO) obj;
            return vo;
        }
        // 其他基本信息
        vo = baseMapper.selectExamById(id);
        // 查询题目项
        List<QuestionItem> questionItems = questionMapper.selectQuesItems(id);
        vo.setQuestionItems(questionItems);

        //设置缓存
        redisCache.setCacheObject(key,vo,10, TimeUnit.MINUTES);
        return vo;
    }

    @Override
    @Transactional
    public Integer editOrAdd(ExamRespVO vo, Integer createUser) {
        Integer examId = 0;
        Exam exam = new Exam();
        List<QuestionExamRel> relList = null;
        Integer examTime = DateUtils.getExamTime(vo.getBeginTime(), vo.getEndTime());
        // 组卷
        if(vo.getId() == null || vo.getId() == 0){
            // 试卷信息
            exam.setCreateUser(createUser);
            BeanUtils.copyProperties(vo,exam);
            exam.setExamTime(examTime);
            baseMapper.insert(exam);
            // 试卷和题目的联系创建
            examId = exam.getId();
            relList = getExamQuestionRelList(vo.getQuestionItems(), examId);
            questionExamRelService.doSave(relList);
            //试卷和班级的联系(即这张试卷属于哪个班级)
            List<ExamClassesRel> examClassesRels = examclassList(examId, vo.getClassesIds());
            examClassesRelMapper.saveBatch(examClassesRels);
            return examId;
        }
        //清除缓存
        redisCache.deleteObject("exam:"+vo.getId());
        // 编辑试卷
        examId = vo.getId();
        BeanUtils.copyProperties(vo,exam);
        exam.setExamTime(examTime);
        baseMapper.updateById(exam);
        questionExamRelService.doRemove(examId);
        relList = getExamQuestionRelList(vo.getQuestionItems(), examId);
        questionExamRelService.doSave(relList);
        return examId;
    }

    private List<ExamClassesRel> examclassList(Integer examId, List<Integer> classesIds) {
        return classesIds.stream().map(item ->{
            ExamClassesRel rel = new ExamClassesRel();
            rel.setExamId(examId);
            rel.setClassesId(item);
            return rel;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean delById(Integer id) {
        //清除缓存
        redisCache.deleteObject("exam:"+id);
        examClassesRelMapper.delRel(id);
        return baseMapper.delById(id) > 0;
    }

    @Override
    @Transactional
    public List<StuExamRespVO> selectRecentExam(Integer classesId,Integer userId) {
        List<StuExamRespVO> voList = baseMapper.selectRecentExam(classesId);
        voList = voList.stream().map(item ->{
            item.setIsfinished(DateUtils.getFinishedTag(item.getBeginTime(),item.getEndTime()));
            LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ExamRecord::getExamId,item.getId())
                    .eq(ExamRecord::getCreateUser,userId);
            ExamRecord record = examRecordMapper.selectOne(wrapper);
            if(Objects.isNull(record)){
                item.setCompleted(false);
            }else{
                item.setCompleted(true);
            }
            return item;
        }).collect(Collectors.toList());
        return voList;
    }


    /**
     * 查询学生首页数据
     * @param vo
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public IPage<StuExamRespVO> selectStudentExam(ExamQueryReqVO vo,Integer userId) {
        IPage<ExamQueryReqVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());
        IPage<StuExamRespVO> examList = baseMapper.getStuExamList(page, vo);
        List<StuExamRespVO> list = examList.getRecords().stream().map(item -> {
            item.setIsfinished(DateUtils.getFinishedTag(item.getBeginTime(), item.getEndTime()));
            LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ExamRecord::getExamId,item.getId())
                    .eq(ExamRecord::getCreateUser,userId);
            ExamRecord record = examRecordMapper.selectOne(wrapper);
            if(Objects.isNull(record)){
                item.setCompleted(false);
            }else{
                item.setCompleted(true);
            }
            return item;
        }).collect(Collectors.toList());
        examList.setRecords(list);
        return examList;
    }

    /**
     * 批改试卷
     * @param vo
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Double correctExam(ExamQuesReqVO vo, Integer userId) {
        // 系统得分
        Double systemScore = 0d;
        Integer examId = vo.getExamId();
        List<ExamQuestionAnswer> answerList = vo.getAnswers().stream().map(item -> {
            // 查询每一个题目
            Integer questionId = item.getQuestionId();
            Question question = questionMapper.selectByIds(questionId,examId);
            //每一个答题项
            ExamQuestionAnswer questionAnswer = getQuestionAnswer(question, item);
            questionAnswer.setCreateUser(userId);
            questionAnswer.setExamId(vo.getExamId());
            return questionAnswer;
        }).collect(Collectors.toList());
        // 将作答数据进行入库
        questionAnswerService.saveBatch(answerList,10);

        // 将错题数据入库
        Exam exam = examMapper.selectById(vo.getExamId());
        List<Wrong> wrongList = new ArrayList<>();
        List<Wrong> updateWrongList = new ArrayList<>();

        answerList.forEach(item ->{
            if( !Objects.isNull(item.getCorrectError()) && !item.getCorrectError()){
                    Wrong one = wrongService.selectByQuestionIdAndUserId(item.getQuestionId(), userId);
                    Wrong wrong = getWrong(item,exam,userId);
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

        // 统计分数
        for (ExamQuestionAnswer answer : answerList){
            systemScore += answer.getScore();
        }
        // 将考试记录进行入库
        ExamRecord record = new ExamRecord();
        record.setExamId(vo.getExamId());
        record.setCreateUser(userId);
        record.setDoTime(vo.getDoTime());
        record.setTotalScore(systemScore);
        ExamQuestionAnswer questionAnswer = answerList.stream().filter(item -> item.getCorrectError() == null).findAny().orElse(null);
        if(Objects.isNull(questionAnswer)){
            record.setIscorrected(true);
        }else{
            record.setIscorrected(false);
        }
        // 记录入库
        examRecordMapper.insert(record);

        return systemScore;
    }

    /**
     * TODO 问题：如果题目量过大怎么办（达到上十万条及以上）
     * 自动组卷(随机)
     * @param vo
     */
    @Override
    @Transactional
    public String autoGroup(AutoGroupReqVO vo,Integer userId) {
        //总分
        AtomicReference<Double> score = new AtomicReference<>(0d);
        //对于试卷题目的联系
        List<QuestionExamRel> rels = new ArrayList<>();
        List<AutoKeyValue> kvs = questionMapper.selectQuestionCount(vo.getSubjectId());
        String isEnough = judgeQuestionIsEnough(kvs, vo);
        // 如果试卷条件不满足
        if(!isEnough.equals("true")){
            return isEnough;
        }
        // 单选题的数目
        if(vo.getSingleNum() > 0){
            List<Question> questions = questionMapper.selectByCondition(vo.getSubjectId(), QuestionConstant.SINGLE_QUESTION);
            rels =  addQuestion(rels, questions, vo.getSingleNum(),vo.getSingleScore());
        }
        // 多选题
        if(vo.getMutilNum() > 0){
            List<Question> questions = questionMapper.selectByCondition(vo.getSubjectId(), QuestionConstant.MULTI_QUESTION);
            rels = addQuestion(rels, questions, vo.getMutilNum(), vo.getMutilScore());
        }
        // 判断
        if(vo.getJudgeNum() > 0){
            List<Question> questions = questionMapper.selectByCondition(vo.getSubjectId(), QuestionConstant.JUDGE_QUESTION);
            rels = addQuestion(rels, questions, vo.getJudgeNum(),vo.getSingleScore());
        }
        // 简答
        if(vo.getWrittenNum() > 0){
            List<Question> questions = questionMapper.selectByCondition(vo.getSubjectId(), QuestionConstant.SHORT_QUESTION);
            rels = addQuestion(rels, questions, vo.getWrittenNum(),vo.getWrittenScore());
        }
        //计算总分
        rels.forEach(item -> score.updateAndGet(v -> v + item.getQuestionScore()));
        log.info("总分是====>{}",score.get());
        log.info("联系表的长度是===>{}",rels.size());
        //新增试卷
        vo.setExamTotal(score.get());
        Exam exam = getExam(vo, userId);
        int count = examMapper.insert(exam);
        rels.forEach(item -> item.setExamId(exam.getId()));
        boolean flag = questionExamRelService.doSave(rels);

        //新增试卷和班级的联系
        List<ExamClassesRel> examClassesRels = examclassList(exam.getId(), vo.getClassesIds());
        examClassesRelMapper.saveBatch(examClassesRels);

        if(!flag){
            return "组卷失败";
        }
        return "true";

    }

    /**
     * 得到成绩列表的数据
     * @param vo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<ScoreRespVO> selectScorePage(ScoreReqVO vo) {
        // 得出考试的基本信息
        IPage<ScoreRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());
        IPage<ScoreRespVO> pages = baseMapper.selectScorePage(page, vo);

        //得到班级人数
        List<CountVO> classNums = classesMapper.selectCountByClassId();

        List<ScoreRespVO> list = pages.getRecords().stream().map(item -> {
            classNums.forEach(cls -> {
                if (cls.getId().equals(item.getClassesId())) {
                    item.setClassesNum(cls.getCount());
                }
            });

            //得到每场考试的实际人数
            item.setActualNum(examClassesRelMapper.selectCountByCondition(item.getExamId(), item.getClassesId()));
            return item;
        }).collect(Collectors.toList());

        pages.setRecords(list);

        return pages;
    }

    private Exam getExam(AutoGroupReqVO vo,Integer userId) {
        Exam exam = new Exam();
        BeanUtils.copyProperties(vo,exam);
        exam.setCreateUser(userId);
        exam.setExamTime(DateUtils.getExamTime(exam.getBeginTime(),exam.getEndTime()));
        return exam;
    }


    /**
     * 得到题目试卷的联系的集合
     * @param items
     * @param examId
     * @return
     */
    private List<QuestionExamRel> getExamQuestionRelList(List<QuestionItem> items,Integer examId){
       return  items.stream().map(item ->{
            QuestionExamRel rel = new QuestionExamRel();
            rel.setExamId(examId);
            rel.setQuestionId(item.getId());
            rel.setQuestionScore(item.getQuestionScore());
            return rel;
        }).collect(Collectors.toList());
    }

    private String getStrAnswer(List<String> answerList){
        return answerList.stream().sorted().collect(Collectors.joining(","));
    }

    private Boolean correct(String  questionAnswer,String answer){
        return questionAnswer.equals(answer);
    }
    private ExamQuestionAnswer getQuestionAnswer(Question question, AnswerItem item){
        ExamQuestionAnswer examQuestionAnswer = new ExamQuestionAnswer();
        Boolean flag = null;
        switch (question.getQuestionType()){
            case QuestionConstant.SINGLE_QUESTION:
            case QuestionConstant.JUDGE_QUESTION:
                flag = correct(question.getCorrectAnswer(), item.getAnswer());
                break;
            case QuestionConstant.MULTI_QUESTION:
                    String answer = getStrAnswer(item.getAnswerList());
                    item.setAnswer(answer);
                    flag = correct(question.getCorrectAnswer(),answer);
                break;
            case QuestionConstant.SHORT_QUESTION:
                flag = null;
                break;
        }
        examQuestionAnswer.setAnswer(item.getAnswer());
        examQuestionAnswer.setQuestionId(item.getQuestionId());
        if(flag != null && flag){
            examQuestionAnswer.setScore(question.getQuestionScore());
            examQuestionAnswer.setCorrectError(true);
        }else{
            examQuestionAnswer.setScore(0d);
            if(flag == null){
                examQuestionAnswer.setCorrectError(null);
            }else{
                examQuestionAnswer.setCorrectError(false);
            }
        }
        return examQuestionAnswer;
    }

    private Wrong getWrong(ExamQuestionAnswer questionAnswer,Exam exam,Integer createUser){
        Wrong wrong = new Wrong();
        wrong.setQuestionId(questionAnswer.getQuestionId());
        wrong.setSubjectId(exam.getSubjectId());
        wrong.setCreateUser(createUser);
        wrong.setAnswer(questionAnswer.getAnswer());
        return wrong;
    }

    private Integer getQuestionNum(List<AutoKeyValue> kvs , Integer questionType){
        for (AutoKeyValue kv : kvs) {
            if(kv.getType() == questionType){
                return kv.getCount();
            }
        }
        return 0;
    }

    private List<QuestionExamRel> addQuestion(List<QuestionExamRel> rels, List<Question> questions, Integer questionNum,Double questionScore){
        Random random = new Random(questions.size());
        for (int i = 0; i < questionNum; i++) {
            Question question = questions.get(random.nextInt(questions.size()));
            QuestionExamRel rel = new QuestionExamRel();
            rel.setQuestionId(question.getId());
            rel.setQuestionScore(questionScore);
            rels.add(rel);
            questions.remove(question);
        }
        return rels;
    }

    private String judgeQuestionIsEnough(List<AutoKeyValue> kvs,AutoGroupReqVO vo){
        //获得单选题
        if(vo.getSingleNum() != 0){
            Integer count = getQuestionNum(kvs, QuestionConstant.SINGLE_QUESTION);
            if(vo.getSingleNum() > count){
                return "题库单选题数目不足，组卷失败";
            }
        }
        //获得多选题
        if (vo.getMutilNum() > 0){
            Integer count = getQuestionNum(kvs, QuestionConstant.MULTI_QUESTION);
            if(vo.getMutilNum() > count){
                return "题库多选题数目不足，组卷失败";
            }
        }
        // 获得判断题
        if (vo.getJudgeNum() > 0) {
            Integer count = getQuestionNum(kvs, QuestionConstant.JUDGE_QUESTION);
            if (vo.getJudgeNum() > count) {
                return "题库判断题数目不足，组卷失败";
            }
        }
        // 获得简答题
        if (vo.getWrittenNum() > 0){
            Integer count = getQuestionNum(kvs, QuestionConstant.SHORT_QUESTION);
            if(vo.getWrittenNum() > count){
                return "题库简答题数目不足，组卷失败";
            }
        }
        return "true";
    }
}




