package com.xyh.exceptions;

import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationExceptionCatch {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResultAPI handleException(BadCredentialsException e){
        return new ResultAPI(ResponseConstant.FAILED,"账号或密码错误","");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResultAPI handleException(AccessDeniedException e){
        return new ResultAPI(ResponseConstant.FAILED,"权限不足","");
    }
}
