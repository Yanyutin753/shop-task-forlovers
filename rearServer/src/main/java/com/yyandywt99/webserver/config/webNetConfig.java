package com.yyandywt99.webserver.config;

/**
 * @author Yangyang
 * @create 2023-09-22 19:08
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webNetConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/dist/**")
                .addResourceLocations("classpath:/static/dist/");
    }
}
