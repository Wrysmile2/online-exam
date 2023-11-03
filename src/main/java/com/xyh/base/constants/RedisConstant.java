package com.xyh.base.constants;

public interface RedisConstant {
    // 用于存储登录信息
    String LOGIN_PREFIX = "edu-login:";

    // 用hash存储单个用户信息
    String USER = "huser";

    String MQ_MSG_CONSUMER = "mq-msg-consumer:";
}
