package com.xyh.config.mvc.security;

import com.alibaba.fastjson.JSON;
import com.xyh.base.constants.CommonConstant;
import com.xyh.base.constants.RedisConstant;
import com.xyh.config.cache.RedisCache;
import com.xyh.config.mvc.security.vo.LoginUser;
import com.xyh.exceptions.CustomException;
import com.xyh.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 解析token的过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final RedisCache redisCache;

    @Autowired
    public JwtAuthenticationTokenFilter(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader(CommonConstant.TOKEN);
        String redisKey = "";
        // 无token，那么就放行，可能是登录
        if(!Strings.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        // 解析token
        try {
            Claims claims = JwtUtil.parseJWT(token); //解析token
            redisKey = RedisConstant.LOGIN_PREFIX +claims.getSubject();
        } catch (Exception e) {
           log.error( e.getMessage());
           throw new CustomException("令牌过期，请重新登录");
        }
        // 因为热部署的原因，所以就取Object，再进行序列化一次
        Object obj = redisCache.getCacheObject(redisKey);
        LoginUser user = JSON.parseObject(JSON.toJSONString(obj), LoginUser.class);
        // 非法redisKey
        if(Objects.isNull(user)){
            throw new CustomException("非法用户");
        }

        //刷新token和redis的时间
        redisCache.expire(redisKey,180, TimeUnit.MINUTES);


        //获取权限
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());

        //设置SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(request,response);

    }
}
