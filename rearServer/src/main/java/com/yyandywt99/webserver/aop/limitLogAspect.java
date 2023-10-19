package com.yyandywt99.webserver.aop;

import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Retention;
import java.util.Arrays;

/**
 * @author Yangyang
 * @create 2023-10-07 19:18
 */
@Slf4j
@Component
@Aspect
public class limitLogAspect {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Around("@annotation(com.yyandywt99.webserver.anno.limitLog)")
    public Object recordLod(ProceedingJoinPoint joinPoint) throws Throwable {
        String jwt = httpServletRequest.getHeader("Authorization");
        Integer operateUser = null;
        if(jwt != null){
            jwt = jwt.substring(7);
            Claims claims = JwtUtils.parseJWT(jwt);
            //登陆人ID
            operateUser = (Integer) claims.get("id");
        }
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);
        log.info(methodParams);

        Object proceed = joinPoint.proceed();
        return proceed;

    }
}
