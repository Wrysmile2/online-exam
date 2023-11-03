package com.xyh.service.impl;

import com.xyh.mapper.ExamMapper;
import com.xyh.mapper.LogMapper;
import com.xyh.mapper.QuestionMapper;
import com.xyh.mapper.UserMapper;
import com.xyh.service.IndexService;
import com.xyh.utils.DateUtils;
import com.xyh.vo.response.admin.AdminIndexRespVO;
import com.xyh.vo.response.other.KeyValue;
import com.xyh.vo.response.teacher.IndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    private UserMapper userMapper;
    private QuestionMapper questionMapper;
    private ExamMapper examMapper;
    private LogMapper logMapper;

    @Autowired
    public IndexServiceImpl(UserMapper userMapper, QuestionMapper questionMapper ,LogMapper logMapper,ExamMapper examMapper) {
        this.userMapper = userMapper;
        this.questionMapper = questionMapper;
        this.logMapper = logMapper;
        this.examMapper = examMapper;
    }

    @Override
    @Transactional
    public AdminIndexRespVO getAdminIndex() {
        AdminIndexRespVO vo = new AdminIndexRespVO();
        vo.setPersonData(userMapper.getUserKeyValue());
        vo.setQuesData(questionMapper.getQuestionKeyValue());
//        List<KeyValue> activeData = logMapper.getActiveData(DateUtils.getCurrentMinTime(), DateUtils.getCurrentMaxTime());
        List<KeyValue> activeData = logMapper.getActiveData();
        List<String> monthDays = DateUtils.getCurrentMonthDays();
        List<Integer> vals = monthDays.stream().map(item -> {
            KeyValue kv = activeData.stream().filter(keyValue -> keyValue.getName().equals(item)).findAny().orElse(null);
            return kv == null ? 0 : kv.getValue();
        }).collect(Collectors.toList());
        vo.setXdata(monthDays);
        vo.setYdata(vals);
        return vo;
    }

    @Override
    public IndexVO getTeacherIndex() {
        IndexVO vo = new IndexVO();
        List<KeyValue> questionKeyValue = questionMapper.getQuestionKeyValue();
        vo.setQuesData(questionKeyValue);
        List<KeyValue> examKVS = examMapper.getExamKVS();
        vo.setExamData(examKVS);
        return vo;
    }
}
