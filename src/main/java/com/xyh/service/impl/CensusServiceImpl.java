package com.xyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyh.base.constants.RedisConstant;
import com.xyh.config.cache.RedisCache;
import com.xyh.mapper.LogMapper;
import com.xyh.mapper.UserMapper;
import com.xyh.pojo.User;
import com.xyh.service.CensusService;
import com.xyh.vo.response.admin.VisitCountRespVO;
import com.xyh.vo.response.other.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CensusServiceImpl implements CensusService {

    private final RedisCache redisCache;

    private final UserMapper userMapper;

    private final LogMapper logMapper;

    @Autowired
    public CensusServiceImpl(RedisCache redisCache, UserMapper userMapper, LogMapper logMapper) {
        this.redisCache = redisCache;
        this.userMapper = userMapper;
        this.logMapper = logMapper;
    }

    @Override
    public VisitCountRespVO getVisitData() {

        VisitCountRespVO vo = new VisitCountRespVO();

        Integer onlineCount = redisCache.keysCount(RedisConstant.LOGIN_PREFIX + "*");
        vo.setOnlineCount(onlineCount);
        Integer visitCount = logMapper.getVisitCurMonthCount();
        vo.setVisitCount(visitCount);
        Integer registerCount = userMapper.getCurMonthRegisterCount();
        vo.setRegisterCount(registerCount);

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getDeleted,false);
        Integer userCount = userMapper.selectCount(wrapper);

        vo.setUserCount(userCount);


        return vo;
    }

    @Override
    public Map<String, Object> getActiveData(List<String> times, List<Integer> diffDays) {

        List<KeyValue> list = logMapper.getActiveDataByCondition(times.get(0), times.get(1), diffDays);
        Map<String,Object> map = new HashMap<>();
        List<String> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        int index = list.get(0).getName().indexOf("-");
        list.stream().forEach(item ->{
            x.add(item.getName().substring(index+1));
            y.add(item.getValue());
        });
        map.put("xData",x);
        map.put("yData",y);

        return map;
    }


}
