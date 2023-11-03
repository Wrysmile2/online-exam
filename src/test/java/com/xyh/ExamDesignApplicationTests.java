package com.xyh;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.constants.RedisConstant;
import com.xyh.config.cache.RedisCache;
import com.xyh.mapper.*;
import com.xyh.pojo.User;
import com.xyh.service.*;
import com.xyh.utils.DateUtils;
import com.xyh.vo.request.admin.MessageReqVO;
import com.xyh.vo.request.admin.MessageSendReqVO;
import com.xyh.vo.request.admin.QuestionPageReqVO;
import com.xyh.vo.request.other.PageVO;
import com.xyh.vo.request.student.ExamQueryReqVO;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.request.teacher.ClassesReqVO;
import com.xyh.vo.request.teacher.ScoreReqVO;
import com.xyh.vo.request.teacher.ToMarkExamReqVO;
import com.xyh.vo.response.admin.MessageRespVO;
import com.xyh.vo.response.other.KeyValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class ExamDesignApplicationTests {

    @Autowired
    private UserService service;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamService examService;

    @Test
    void testExamRespVO(){
        System.out.println(examMapper.selectExamById(1));
    }

    @Test
    void contextLoads() {
        Integer[] ids = new Integer[2];
        ids[0] = 1;
        ids[1] = 2;
        subjectService.delByIds(ids);
//       service.updateStatus(ids);
    }

    @Test
    void testMessage(){
        MessageReqVO vo = new MessageReqVO();
        vo.setPageIndex(1L);
        vo.setPageSize(10L);
        vo.setSendUserName("派大星");
        IPage<MessageRespVO> pageList = messageService.getPageList(vo);
    }
    @Test
    void testSend(){
        MessageSendReqVO vo = new MessageSendReqVO();
        vo.setMessageContent("测试内容");
        vo.setTitle("ces");
        vo.setReceiveUserIds(new ArrayList<>());
        messageService.sendMessage(vo,1);
    }

    @Test
    void testRead(){
        messageService.readMessage(7);
    }

    @Test
    void testKeyValue(){
//        List<KeyValue> list = userMapper.getUserKeyValue();
//        List<KeyValue> list = questionMapper.getAdminQuestionKeyValue();
        String minTime = DateUtils.getCurrentMinTime();
        String maxTime = DateUtils.getCurrentMaxTime();

        List<KeyValue> activeData = logMapper.getActiveData();
        List<String> monthDays = DateUtils.getCurrentMonthDays();
        System.out.println(monthDays);
        List<Integer> collect = monthDays.stream().map(item -> {
            KeyValue kv = activeData.stream().filter(keyValue -> keyValue.getName().equals(item)).findAny().orElse(null);
            if(kv != null){
                System.out.println("=>"+kv.getValue());
            }
        return kv == null ? 0 : kv.getValue();
        }).collect(Collectors.toList());
        collect.forEach(System.out::println);
//        activeData.stream().forEach(item-> System.out.println(item));

//        Map<Integer, Object> map =  logMapper.getActiveData(minTime, maxTime);
//        map.forEach((k,v)->{
//            System.out.println(k+"\t"+v);
//        });

//        TreeMap<Integer, Object> diff = Utils.makeUpActiveDiff(map);
//        diff.forEach((k,v)->{
//            System.out.println(k+"\t"+v);
//        });
    }

    @Test
    void update(){
        User u = new User();
        u.setId(1);
        u.setUserEmail("1222@qq.com");
        userMapper.updateUser(u);
    }

    @Test
    void testQuestion(){
        QuestionPageReqVO vo = new QuestionPageReqVO();
        vo.setQuestionName("AA");
        vo.setQuestionType(0);
        vo.setPageIndex(1L);
        vo.setPageSize(10L);
        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
        vo.setSubjectIds(null);
        questionService.getPageList(vo).getRecords().stream().forEach(System.out::println);
    }

    @Test
    void testSubject(){
//        String subjectName = "";
//        System.out.println(subjectService.getSubjectName(subjectName));
        List<Integer> list = new ArrayList<>();
//        list.add(1);
        questionService.delByIds(list);
    }

    @Test
    void testQuestionItems(){
//        questionMapper.selectQuesItems(1).stream().forEach(item->{
//            System.out.println(item.getQuestionName());
//        });

    }

    @Test
    void testStudentIndex(){
        examService.selectRecentExam(4,1).stream().forEach(System.out::println);
    }


    @Test
    void testStudentSubject(){
        subjectService.selectSubjects().forEach(System.out::println);
    }

    @Test
    void testStuExam(){
        ExamQueryReqVO vo = new ExamQueryReqVO();
        vo.setClassesId(1);
        vo.setIsfinished(0);
        vo.setSubjectId(-1);
        vo.setPageIndex(1L);
        vo.setPageSize(12L);
        examService.selectStudentExam(vo,1).getRecords().stream().forEach(System.out::println);
    }


    @Test
    void testUnreadCount(@Autowired MessageUserService messageUserService){
        System.out.println(messageUserService.selectUnreadCount(1));
    }

    @Test
    void testStuMsgList(){
        PageVO vo = new ExamQueryReqVO();
        vo.setPageIndex(1L);
        vo.setPageSize(12L);
        messageService.selectStuList(vo,1).getRecords().stream().forEach(System.out::println);
    }

    @Test
    void testPracticeList(@Autowired QuestionPracticeService service){
        QueryReqVO vo = new QueryReqVO();
        vo.setUserId(1);
        vo.setPageIndex(1L);
        vo.setPageSize(12L);
        vo.setSubjectId(-1);
        service.selectPracticeList(vo).getRecords().stream().forEach(System.out::println);
    }

    @Test
    void testQuestionById(){
        System.out.println(questionMapper.selectById(1));
    }

    @Test
    void testExamQuestion(){
        questionService.selectQuestionList(11).forEach(System.out::println);
    }


    @Test
    void testQuestionBatch(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        questionMapper.delByIds(list);
    }

    @Test
    void testCLasses(@Autowired ClassesService service){
        ClassesReqVO vo = new ClassesReqVO();
        vo.setPageIndex(1L);
        vo.setPageSize(10L);
        service.selectPage(vo).getRecords().forEach(System.out::println);

    }

    @Test
    void testScoreList(@Autowired ExamService service){
        ScoreReqVO vo = new ScoreReqVO();
        vo.setPageIndex(1L);
        vo.setPageSize(10L);
        service.selectScorePage(vo).getRecords().forEach(System.out::println);
    }

    @Test
    void testScoreDetail(@Autowired ExamRecordService service){
        service.selectScoreList(32,1).forEach(System.out::println);
    }

    @Test
    void testExamRecord(@Autowired ExamRecordService service){
        ToMarkExamReqVO o = new ToMarkExamReqVO();
        o.setPageIndex(1L);
        o.setPageSize(10L);
        service.selectToMarkPageList(o).getRecords().forEach(System.out::println);
    }

    @Test
    void testKeyCount(@Autowired RedisCache redisCache){
        System.out.println(redisCache.keysCount(RedisConstant.LOGIN_PREFIX+"*"));
    }

    @Test
    void testLogActive(@Autowired LogMapper logMapper){
        System.out.println(logMapper.getActiveDataByCondition("2023-03-31", "2023-04-14", DateUtils.getDiffDays("2023-03-31", "2023-04-14")));
    }
}
