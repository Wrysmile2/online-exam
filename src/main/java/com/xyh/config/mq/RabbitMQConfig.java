package com.xyh.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    /**
     * 配置消息转换器
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
/**
 * 消息生产者将消息提交给交换机
 * @param correlationData 存放消息的唯一ID
 * @param ack             消息是否被传递成功
 * @param cause           失败原因
 */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            //如果投递不成功...
            if(!ack){
                log.info("消息投递失败，原因{}",cause);
                return;
            }
            log.info("消息投递成功...messageId{}",correlationData.getId());
        });
        /**
         *  消息从交换机到队列的失败回调
         */
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("消息投递失败....");
        });
        return rabbitTemplate;
    }

}
