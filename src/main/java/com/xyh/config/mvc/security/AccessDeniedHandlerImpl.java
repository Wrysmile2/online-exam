package com.xyh.config.mvc.security;

import com.alibaba.fastjson.JSON;
import com.xyh.base.vo.ResultAPI;
import com.xyh.utils.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足的处理
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResultAPI<String> result = new ResultAPI<>(HttpStatus.FORBIDDEN.value(),"权限不足","" );
        String data = JSON.toJSONString(result);

        WebUtil.renderString(response,data);
    }
}
