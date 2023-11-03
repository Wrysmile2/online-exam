package com.xyh.exceptions;

import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionCatch {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultAPI handleException(RuntimeException e){
        return new ResultAPI(ResponseConstant.FAILED,"服务异常，请稍后重试","");
    }
}
