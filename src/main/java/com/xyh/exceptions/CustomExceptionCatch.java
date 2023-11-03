package com.xyh.exceptions;

import com.xyh.base.constants.ResponseConstant;
import com.xyh.base.vo.ResultAPI;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常捕获
 */
@RestControllerAdvice
public class CustomExceptionCatch {


    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResultAPI handleException(CustomException e){
        return new ResultAPI(ResponseConstant.FAILED,e.getMessage(),"");
    }


}
