package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.base.constants.RabbitMQConstant;
import com.xyh.base.context.WebContext;
import com.xyh.config.cache.RedisCache;
import com.xyh.pojo.Log;
import com.xyh.pojo.Subject;
import com.xyh.pojo.User;
import com.xyh.service.SubjectService;
import com.xyh.mapper.SubjectMapper;
import com.xyh.utils.RabbitMQUtil;
import com.xyh.vo.request.admin.SubjectReqVO;
import com.xyh.vo.response.admin.SubjectRespVO;
import com.xyh.vo.response.other.KeyValue;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @description 针对表【tbl_subject】的数据库操作Service实现
 * @createDate 2022-12-27 22:19:07
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject>
        implements SubjectService {

    private RabbitMQUtil rabbitMQUtil;

    private WebContext webContext;

    private RedisCache redisCache;

    @Autowired
    public SubjectServiceImpl(RabbitMQUtil rabbitMQUtil, WebContext webContext, RedisCache redisCache) {
        this.rabbitMQUtil = rabbitMQUtil;
        this.webContext = webContext;
        this.redisCache = redisCache;
    }

    @Override
    public IPage<SubjectRespVO> getPageList(SubjectReqVO vo) {
        IPage<Subject> page = new Page<>(vo.getPageIndex(), vo.getPageSize());
        LambdaQueryWrapper<Subject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Subject::getDeleted, 0)
                .like(Strings.isNotEmpty(vo.getSubjectName()), Subject::getSubjectName, vo.getSubjectName())
                .orderByDesc(Subject::getItemOrder);
        IPage<Subject> pages = baseMapper.selectPage(page, wrapper);
        if (pages.getCurrent() < vo.getPageIndex()) {
            vo.setPageIndex(pages.getCurrent());
            page = new Page<>(vo.getPageIndex(), vo.getPageSize());
            pages = baseMapper.selectPage(page, wrapper);
        }
        IPage<SubjectRespVO> respPage = new Page<>();
        respPage.setRecords(pages.getRecords().stream().map(item -> {
                    SubjectRespVO respVO = new SubjectRespVO();
                    BeanUtils.copyProperties(item, respVO);
                    return respVO;
                }
        ).collect(Collectors.toList()));
        respPage.setPages(pages.getPages());
        respPage.setCurrent(pages.getCurrent());
        return respPage;
    }

    @Override
    public void delByIds(Integer[] ids) {
        List<Subject> list = baseMapper.selectBatchIds(Arrays.asList(ids));
        list = list.stream().map(item -> {
            item.setDeleted(true);
            return item;
        }).collect(Collectors.toList());
        //清理缓存
        list.stream().forEach(item -> redisCache.deleteObject("subject:" + item));
        list.stream().forEach(item -> baseMapper.updateById(item));
    }

    @Override
    @Transactional
    public boolean editSubject(Subject subject) {
        int count = 0;
        if (subject.getId() == null) {
            count = baseMapper.insert(subject);
            rabbitMQUtil.sendMsg(new Log(getUser().getUsername() + "新增科目:" + subject.getSubjectName(), getUser().getUsername(), getUser().getId(), false, new Date()));
        } else {
            //删除缓存
            redisCache.deleteObject("subject:" + subject.getId());
            Subject one = baseMapper.selectById(subject.getId());
            count = baseMapper.updateById(subject);
            rabbitMQUtil.sendMsg(new Log(getUser().getUsername() + "将科目名:[" + one.getSubjectName() + "]修改为:[" + subject.getSubjectName()+"]",
                    getUser().getUsername(), getUser().getId(), false, new Date()));
        }
        return count > 0;
    }

    @Override
    public List<KeyValue> getSubjectName(String subjectName) {
        return baseMapper.selectSubjectName(subjectName);
    }

    @Override
    public List<KeyValue> selectSubjects() {
        return baseMapper.selectSubjects();
    }

    @Override
    public Subject getSubjectById(Integer id) {
        Subject subject = null;
        Object obj = redisCache.getCacheObject("subject:" + id);
        if (!Objects.isNull(obj)) {
            subject = (Subject) obj;
        } else {
            subject = baseMapper.selectById(id);
            redisCache.setCacheObject("subject:" + id, subject, 10, TimeUnit.MINUTES);
        }
        return subject;
    }


    /**
     * 简化得到User
     *
     * @return
     */
    private User getUser() {
        return webContext.getCurrentUser();
    }
}
