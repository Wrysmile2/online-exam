package com.xyh.service;

import com.xyh.pojo.QuestionExamRel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_question_exam_rel】的数据库操作Service
* @createDate 2022-12-27 22:23:53
*/
public interface QuestionExamRelService extends IService<QuestionExamRel> {

    /**
     * 批量保存题目与试卷的联系
     * @param list
     * @return
     */
    boolean doSave(List<QuestionExamRel> list);

    /**
     * 批量删除题目与试卷的联系
     * @param examId 试卷Id
     */
    void doRemove(Integer examId);
}
