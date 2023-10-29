package com.yyandywt99.webserver;

import com.yyandywt99.webserver.config.TaskSchedulerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

//定时注解2
@Import(TaskSchedulerConfig.class)
//定时注解
@EnableScheduling
//filter启动注解
@ServletComponentScan
//springBoot启动注解
@SpringBootApplication
public class WebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);
    }

}
