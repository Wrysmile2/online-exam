package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.base.constants.RabbitMQConstant;
import com.xyh.base.context.WebContext;
import com.xyh.config.cache.RedisCache;
import com.xyh.pojo.Classes;
import com.xyh.pojo.Log;
import com.xyh.pojo.User;
import com.xyh.service.ClassesService;
import com.xyh.mapper.ClassesMapper;
import com.xyh.utils.RabbitMQUtil;
import com.xyh.vo.request.teacher.ClassesReqVO;
import com.xyh.vo.response.teacher.ClassesRespVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
* @author 肖远华
* @description 针对表【tbl_classes】的数据库操作Service实现
* @createDate 2023-03-15 11:55:53
*/
@Service
public class ClassesServiceImpl extends ServiceImpl<ClassesMapper, Classes>
    implements ClassesService{

    private final RabbitMQUtil rabbitMQUtil;

    private final WebContext webContext;

    private final RedisCache redisCache;

    @Autowired
    public ClassesServiceImpl(RabbitMQUtil rabbitMQUtil, WebContext webContext,RedisCache redisCache) {
        this.rabbitMQUtil = rabbitMQUtil;
        this.webContext = webContext;
        this.redisCache = redisCache;
    }

    @Override
    public List<Classes> selectByClassName(String classesName) {
        LambdaQueryWrapper<Classes> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!classesName.equals("_"),Classes::getClassesName,classesName)
                .eq(Classes::getDeleted,false);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public IPage<ClassesRespVO> selectPage(ClassesReqVO vo) {

        IPage<ClassesRespVO> page = new Page<>(vo.getPageIndex(),vo.getPageSize());

        IPage<ClassesRespVO> pages = baseMapper.selectPageList(page, vo);
        return pages;
    }

    @Override
    public void edit(Classes vo, Integer id) {
        // 新增班级
        if(Objects.isNull(vo.getId())){
            vo.setCreateUser(id);
            vo.setDeleted(false);
            baseMapper.insert(vo);
            rabbitMQUtil.sendMsg(new Log(getUser().getUsername()+"新增了班级:["+vo.getClassesName()+"]",getUser().getUsername(),getUser().getId(),false,new Date()));
        }else {
            Classes classes = baseMapper.selectById(vo.getId());
            baseMapper.updateById(vo);
            //清除缓存
            redisCache.deleteObject("clazz:"+vo.getId());
            rabbitMQUtil.sendMsg(new Log(getUser().getUsername()+"将班级名:["+classes.getClassesName()+"]修改为:["+vo.getClassesName()+"]",
                    getUser().getUsername(),getUser().getId(),false,new Date()));
        }
    }

    @Override
    public void delBatch(List<Integer> ids) {
        //清理缓存
        ids.stream().forEach(item -> redisCache.deleteObject("clazz:"+item));
        baseMapper.delBatch(ids);
    }

    /**
     * 得到班级ID
     * @param id
     * @return
     */
    @Override
    public Classes getClassById(Integer id) {
        String key = "clazz:"+id;
        Classes clazz = null;
        Object obj = redisCache.getCacheObject(key);
        if(!Objects.isNull(obj)){
            clazz = (Classes) obj;
            return clazz;
        }
        clazz = baseMapper.selectById(id);
        redisCache.setCacheObject(key,clazz,10, TimeUnit.MINUTES);
        return clazz;
    }


/*    public void send(Log log){
        rabbitTemplate.convertAndSend(RabbitMQConstant.EXAM_EXCHANGE,RabbitMQConstant.LOG_KEY,log);
    }*/

    private User getUser(){
        return webContext.getCurrentUser();
    }

}




