package com.xyh.config.sysLog;

import com.xyh.base.context.WebContext;
import com.xyh.pojo.Log;
import com.xyh.pojo.User;
import com.xyh.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 设置切面
 */

@Slf4j
@Aspect
@Component
public class SysLogUtil {

    private final LogService logService;
    private final WebContext webContext;

    @Autowired
    public SysLogUtil(LogService logService,WebContext webContext) {
        this.logService = logService;
        this.webContext = webContext;
    }

    /**
     * 设置切点
     */
    @Pointcut("@annotation(com.xyh.config.sysLog.SysLog)")
    public void serviceAspect(){

    }

    /**
     * 后置
     * @param joinPoint
     */
    @After("serviceAspect()")
    public void doService(JoinPoint joinPoint){
        User user = webContext.getCurrentUser();
        Object[] args = joinPoint.getArgs();
        // 记录日志
        Log log = new Log();
        log.setCreateUser(user.getId());
        log.setLogContent(getContent(joinPoint));

        logService.save(log);

    }

    /**
     * 获取注解中的日志内容信息
     * @param joinPoint
     * @return
     */
    private String getContent(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        Signature signature = joinPoint.getSignature();
        Class clazz = signature.getDeclaringType();
        String name = signature.getName();

        //获得MethodSignature
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SysLog annotation = method.getAnnotation(SysLog.class);
        String content = annotation.content();
        return content;
    }
}
