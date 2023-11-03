package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.mapper.SubjectMapper;
import com.xyh.pojo.Subject;
import com.xyh.pojo.Wrong;
import com.xyh.service.WrongService;
import com.xyh.mapper.WrongMapper;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.response.student.WrongDataItem;
import com.xyh.vo.response.student.WrongIndexVO;
import com.xyh.vo.response.student.WrongRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author xyh
* @description 针对表【tbl_wrong】的数据库操作Service实现
* @createDate 2023-02-28 11:00:04
*/
@Service
public class WrongServiceImpl extends ServiceImpl<WrongMapper, Wrong>
    implements WrongService{

    private SubjectMapper subjectMapper;

    @Autowired
    public WrongServiceImpl(SubjectMapper subjectMapper) {
        this.subjectMapper = subjectMapper;
    }

    @Override
    public Wrong selectByQuestionIdAndUserId(Integer questionId, Integer userId) {
        return baseMapper.selectByQuestionIdAndCreateUser(questionId,userId);
    }

    @Override
    public IPage<WrongIndexVO> pageList(QueryReqVO vo) {
        IPage<WrongIndexVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());

        IPage<WrongIndexVO> pages = baseMapper.selectWrongList(page, vo);
        return pages;
    }

    @Override
    @Transactional(readOnly = true)
    public WrongRespVO selectWrongsByPage(QueryReqVO vo) {
        WrongRespVO mode = new WrongRespVO();

        Subject subject = subjectMapper.selectById(vo.getSubjectId());
        mode.setSubjectName(subject.getSubjectName());
        IPage<WrongDataItem> page = new Page<>(vo.getPageIndex(),vo.getPageSize());

        IPage<WrongDataItem> pages = baseMapper.selectWrong(page, vo);
        if(pages.getPages() < vo.getPageIndex()){
            page = new Page<>(pages.getPages(),vo.getPageSize());
            pages = baseMapper.selectWrong(page,vo);
        }
        mode.setWrongs(pages.getRecords());
        return mode;
    }
}




