package com.xyh.config.mq;

import com.rabbitmq.client.Channel;
import com.xyh.base.constants.RabbitMQConstant;
import com.xyh.base.constants.RedisConstant;
import com.xyh.config.cache.RedisCache;
import com.xyh.pojo.Log;
import com.xyh.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 消息队列消息的消费者
 */
@Slf4j
@Component
public class MQConsumer {


    private final LogService logService;

    private final RedisCache redisCache;

    @Autowired
    public MQConsumer(LogService logService, RedisCache redisCache) {
        this.logService = logService;
        this.redisCache = redisCache;
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitMQConstant.LOG_QUEUE),
            exchange = @Exchange(name = RabbitMQConstant.EXAM_EXCHANGE, type = "direct"),
            key = RabbitMQConstant.LOG_KEY
    ))
    @Transactional(rollbackFor = RuntimeException.class)
    public void consumerLog(Log userLog, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                            @Header("spring_returned_message_correlation") String correlationId){
        if(!redisCache.ifAbsent(RedisConstant.MQ_MSG_CONSUMER+correlationId, correlationId)){
            try {
                channel.basicAck(tag, false);
            } catch (IOException e) {
                log.info(e.getMessage());
            }
            return;
        }
        logService.save(userLog);
        // 设置消息已经被消费
        redisCache.setCacheObject(RedisConstant.MQ_MSG_CONSUMER+correlationId,correlationId,5, TimeUnit.MINUTES);
        try {
            channel.basicAck(tag, false);
        } catch (IOException e) {
            log.info("消费消息出现异常===》{}",e.getMessage());
        }
    }



}
