package com.xyh.config.sysLog;

import com.xyh.pojo.Log;
import com.xyh.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 用户登录日志监听，采用AOP无法获取到相应的用户编号等信息，其他日志采用AOP
 */
@Component
public class UserLogListener implements ApplicationListener<UserEvent> {

    private LogService logService;

    @Autowired
    public UserLogListener(LogService logService) {
        this.logService = logService;
    }


    @Override
    public void onApplicationEvent(UserEvent event) {
        logService.save(event.getLog());
    }
}
