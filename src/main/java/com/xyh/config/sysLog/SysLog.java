package com.xyh.config.sysLog;

import java.lang.annotation.*;

/**
 * 日志的注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
public @interface SysLog {

    String content() default "";
    boolean isLogin() default false;

}
