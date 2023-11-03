package com.xyh.utils;

import com.xyh.base.constants.RabbitMQConstant;
import com.xyh.pojo.Log;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RabbitMQUtil {


    private final RabbitTemplate template;

    @Autowired
    public RabbitMQUtil(RabbitTemplate template) {
        this.template = template;
    }

    public  void sendMsg(Log log){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        template.convertAndSend(RabbitMQConstant.EXAM_EXCHANGE, RabbitMQConstant.LOG_KEY, log, correlationData);
    }
}
