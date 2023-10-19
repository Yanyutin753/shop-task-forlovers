package com.yyandywt99.webserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//filter启动注解
@ServletComponentScan
//springBoot启动注解
@SpringBootApplication
public class WebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServerApplication.class, args);
    }

}
