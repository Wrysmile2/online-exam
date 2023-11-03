package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.pojo.QuestionExamRel;
import com.xyh.service.QuestionExamRelService;
import com.xyh.mapper.QuestionExamRelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_question_exam_rel】的数据库操作Service实现
* @createDate 2022-12-27 22:23:53
*/
@Service
public class QuestionExamRelServiceImpl extends ServiceImpl<QuestionExamRelMapper, QuestionExamRel>
    implements QuestionExamRelService{

    @Override
    public boolean doSave(List<QuestionExamRel> list) {
        return saveBatch(list);
    }

    @Override
    public void doRemove(Integer examId) {
        LambdaQueryWrapper<QuestionExamRel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QuestionExamRel::getExamId,examId);
        baseMapper.delete(wrapper);
    }
}




