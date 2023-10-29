package com.yyandywt99.webserver.filter;

import com.alibaba.fastjson.JSONObject;
import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpServerConnection;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Yangyang
 * @create 2023-09-27 20:10
 */
@Slf4j
@CrossOrigin
//@WebFilter(urlPatterns = "/*")
public class Loginfiler implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        HttpServletResponse servletResponse1 = (HttpServletResponse) servletResponse;
        servletResponse1.setHeader("Access-Control-Allow-Origin", "*"); // 允许跨域访问的来源
        servletResponse1.setHeader("Access-Control-Allow-Methods", "*"); // 允许的请求方法
        servletResponse1.setHeader("Access-Control-Allow-Headers", "*"); // 允许的请求头
        servletResponse1.setHeader("Access-Control-Allow-Credentials", "true"); // 允许发送凭据（例如，带有Cookie的请求）


        String url = servletRequest1.getRequestURI();
        log.info("请求的url: {}",url);

        if(url.contains("login")){
            filterChain.doFilter(servletRequest,servletResponse);
            System.out.println("放行成功！");
            return;
        }
        String jwt = servletRequest1.getHeader("token");
        log.info(jwt);
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头内容为空");
            Result error = Result.error("no loading");
            String res = JSONObject.toJSONString(error);
            servletResponse1.getWriter().write(res);
            return;
        }
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.info("未登录");
            e.printStackTrace();
            Result error = Result.error("no loading");
            String res = JSONObject.toJSONString(error);
            servletResponse1. getWriter().write(res);
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
        log.info("放行成功");
    }
}
