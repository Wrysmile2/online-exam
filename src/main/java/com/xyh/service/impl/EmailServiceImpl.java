package com.xyh.service.impl;

import com.xyh.service.EmailService;
import com.xyh.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 邮件发送的类
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // 发送者
    @Value("${spring.mail.username}")
    private String from;
    // 主体
    private String subject = "XYH在线教育考试";

    @Override
    public String sendCode(String userEmail) {
        String code = Utils.generatorValidationCode();
        // 简单邮件服务
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from+"(官方信息)");
        message.setTo(userEmail);
        message.setSubject(subject);
        message.setText(code);
        mailSender.send(message);
        return code;
    }

    @Override
    public void sendRestPass(String userEmail,String pass){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from+"(密码重置-EDU)");
        message.setTo(userEmail);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(pass);
        mailSender.send(message);
    }
}
