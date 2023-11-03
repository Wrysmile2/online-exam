package com.xyh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ExamDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamDesignApplication.class, args);
        log.info("项目构建成功");
    }

}
