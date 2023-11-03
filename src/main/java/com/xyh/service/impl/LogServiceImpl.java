package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyh.mapper.UserMapper;
import com.xyh.pojo.Log;
import com.xyh.pojo.User;
import com.xyh.service.LogService;
import com.xyh.mapper.LogMapper;
import com.xyh.vo.request.admin.LogReqVO;
import com.xyh.vo.response.admin.LogRespVO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author xyh
* @description 针对表【tbl_log】的数据库操作Service实现
* @createDate 2022-12-27 22:26:14
*/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
    implements LogService{

    private UserMapper userMapper;

    @Autowired
    public LogServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public IPage<LogRespVO> getPageList(LogReqVO vo) {
        LambdaQueryWrapper<Log> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(vo.getCreateUser() != null,Log::getCreateUser,vo.getCreateUser())
                .like(Strings.isNotEmpty(vo.getUsername()),Log::getUsername,vo.getUsername())
                .orderByDesc(Log::getCreateTime);
        IPage<Log> page = new Page<>(vo.getPageIndex(),vo.getPageSize());
        IPage<Log> pages = baseMapper.selectPage(page, wrapper);
        if(pages.getPages() < vo.getPageIndex()){
            vo.setPageIndex(pages.getPages());
            page = new Page<>(vo.getPageIndex(),vo.getPageSize());
            pages = baseMapper.selectPage(page,wrapper);
        }
        List<LogRespVO> vos = pages.getRecords().stream().map(item -> {
            LogRespVO respVO = new LogRespVO();
            BeanUtils.copyProperties(item, respVO);
            User user = userMapper.selectById(item.getCreateUser());
            respVO.setAccount(user.getAccount());
            respVO.setUsername(user.getUsername());
            return respVO;
        }).collect(Collectors.toList());
        IPage<LogRespVO> logPages = new Page<>();
        BeanUtils.copyProperties(pages,logPages);
        logPages.setRecords(vos);
        return logPages;
    }

    @Override
    public IPage<Log> getUserLog(Integer userId) {
            IPage<Log> page = new Page<>(1,15);
            LambdaQueryWrapper<Log> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Log::getCreateUser,userId).orderByDesc(Log::getCreateTime);
            IPage<Log> pages =baseMapper.selectPage(page,wrapper);
            return pages;
    }
}




