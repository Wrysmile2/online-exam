package com.xyh.config.mvc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 设置跨域
 */
@Slf4j
@Configuration
public class CorsConfig  implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")   //允许跨域的路径
                .allowedOriginPatterns("*")    //允许跨域的域名
                .allowCredentials(true)        // 允许cookie
                .allowedMethods("POST","GET")   // 请求方式
                .allowedHeaders("*")            // 允许的头部
                .maxAge(3600);
    }
}
