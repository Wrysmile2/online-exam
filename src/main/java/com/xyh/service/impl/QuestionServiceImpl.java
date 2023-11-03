package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.config.cache.RedisCache;
import com.xyh.pojo.Question;
import com.xyh.service.QuestionService;
import com.xyh.mapper.QuestionMapper;
import com.xyh.utils.HTMLUtil;
import com.xyh.vo.request.admin.AddQuestionPageReqVO;
import com.xyh.vo.response.admin.AddQuestionPageRespVO;
import com.xyh.vo.request.admin.QuestionPageReqVO;
import com.xyh.vo.response.admin.QuestionPageRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
* @author xyh
* @description 针对表【tbl_question】的数据库操作Service实现
* @createDate 2022-12-27 22:25:21
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

    private RedisCache redisCache;

    @Autowired
    public QuestionServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    /**
     * 分页数据
     * @param vo
     * @return
     */
    @Override
    public IPage<QuestionPageRespVO> getPageList(QuestionPageReqVO vo) {
        if(vo.getSubjectIds() != null && vo.getSubjectIds().size() <= 0 ){
            vo.setSubjectIds(null);
        }
        IPage<QuestionPageRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());

        IPage<QuestionPageRespVO> pages = baseMapper.getPageList(page, vo);

        if(pages.getPages() < vo.getPageIndex()){
            page = new Page<>(pages.getPages(),vo.getPageSize());
            pages = baseMapper.getPageList(page,vo);
        }
        pages.getRecords().forEach(item ->{
            item.setQuestionName(HTMLUtil.clear(item.getQuestionName()));
        });
        return pages;
    }

    @Override
    public void delByIds(List<Integer> ids) {
        if(ids.size() > 0){
            ids.forEach(item -> redisCache.deleteObject("question:"+item));
            baseMapper.delByIds(ids);
        }
    }


    @Override
    public Question previewQuestion(Integer id) {
        String key = "question:"+id;
        Question question = null;
        Object obj = redisCache.getCacheObject(key);
        if(!Objects.isNull(obj)){
            question = (Question) obj;
            return question;
        }
        question = baseMapper.selectById(id);
        //新增缓存
        redisCache.setCacheObject(key,question,10, TimeUnit.MINUTES);
        return  question;
    }

    @Override
    public boolean editOrAdd(Question vo) {
        int count = 0;
        if(vo.getId() != null && vo.getId() != 0){
            // 编辑
            //清除缓存
            redisCache.deleteObject("question:"+vo.getId());
            count = baseMapper.updateById(vo);
        }else{
            // 新增
            count = baseMapper.insert(vo);
        }
        return count > 0;
    }


    @Override
    public IPage<AddQuestionPageRespVO> getAddPageList(AddQuestionPageReqVO vo) {
        IPage<AddQuestionPageRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());
        IPage<AddQuestionPageRespVO> pages = baseMapper.getAddPageList(page,vo);
        return pages;
    }

    /**
     * 每张试卷题目的查询
     * @param examId
     * @return
     */
    @Override
    public List<Question> selectQuestionList(Integer examId) {
        /*List<Question> list = null;
        String key = "exam-question:"+examId;
        Object obj = redisCache.getCacheObject(key);
        if(!Objects.isNull(obj)){
            list = (List<Question>) obj;
            return list;
        }*/
        return  baseMapper.selectQuestionListByExamId(examId);
    }
}




