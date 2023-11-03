package com.xyh.config.sysLog;

import com.xyh.pojo.Log;
import org.springframework.context.ApplicationEvent;


public class UserEvent extends ApplicationEvent {

    private Log log;

    public UserEvent(  Log log) {
        super(log);
        this.log = log;
    }

    public Log getLog(){
        return log;
    }


}
