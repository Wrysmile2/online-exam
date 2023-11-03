package com.xyh.config.mvc.security;

import com.alibaba.fastjson.JSON;
import com.xyh.base.vo.ResultAPI;
import com.xyh.utils.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未授权的用户
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ResultAPI<String> result = new ResultAPI<>(HttpStatus.UNAUTHORIZED.value(), "鉴权失败", "");
        String data = JSON.toJSONString(result);

        WebUtil.renderString(response,data);

    }
}
