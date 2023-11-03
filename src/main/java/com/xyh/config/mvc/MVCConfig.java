package com.xyh.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * swagger中使用WebMvcConfigurationSupport会出现页面找不到，所以使用
 */
@Configuration
public class MVCConfig implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/","/student/index.html");
        registry.addRedirectViewController("/student","/student/index.html");
        registry.addRedirectViewController("/teacher","/teacher/index.html");
        registry.addRedirectViewController("/admin","/admin/index.html");
    }

    /**
     * 配置资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/avatar/**")
                .addResourceLocations("file:D:\\IDEA\\ExamDesign\\src\\main\\resources\\static\\avatar\\")
                .setCachePeriod(31556926);

        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:META-INF/resources/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:META-INF/resources/webjars/");
    }
}
