package com.xyh.base.context;

import com.alibaba.fastjson.JSON;
import com.xyh.base.constants.RedisConstant;
import com.xyh.config.cache.RedisCache;
import com.xyh.config.mvc.security.vo.LoginUser;
import com.xyh.pojo.User;
import com.xyh.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * 用来获取当前用户的类
 */
@Slf4j
@Component
public class WebContext {

    private final RedisCache redisCache;

    private final UserService userService;

    public WebContext(RedisCache redisCache, UserService userService) {
        this.redisCache = redisCache;
        this.userService = userService;
    }



    public User getCurrentUser(){
        LoginUser principal = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object obj = redisCache.getCacheMapValue(RedisConstant.USER, String.valueOf(principal.getUser().getId()));
        User user = null;
        user = JSON.parseObject(JSON.toJSONString(obj),User.class);
        if(Objects.isNull(obj)){
            user = userService.getById(principal.getUser().getId());
        }

        return user;
    }

}
