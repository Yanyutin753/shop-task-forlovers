package com.yyandywt99.webserver.aop;

import com.alibaba.fastjson.JSONObject;
import com.yyandywt99.webserver.mapper.OperateLogMapper;
import com.yyandywt99.webserver.pojo.OperateLog;
import com.yyandywt99.webserver.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author Yangyang
 * @create 2023-10-07 13:35
 */
@Slf4j
@Component
@Aspect //切面类
public class LogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Autowired
    private HttpServletRequest httpServletRequest;


    @Around("@annotation(com.yyandywt99.webserver.anno.Log)")

    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String jwt = httpServletRequest.getHeader("Authorization");
        Integer operateUser = null;
        if(jwt != null){
            jwt = jwt.substring(7);
            Claims claims = JwtUtils.parseJWT(jwt);
            //登陆人ID
            operateUser = (Integer) claims.get("id");
        }
        //操作时间
        LocalDateTime operateTime = LocalDateTime.now();
        //操作类名
        String className = joinPoint.getTarget().getClass().getName();
        //操作方法名
        String methodName = joinPoint.getSignature().getName();
        //调用方法参数
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);
        //操作开始时间
        long begin = System.currentTimeMillis();
        //调用原始方法运行
        Object result = joinPoint.proceed();

        //方法返回值
        String returnValue = JSONObject.toJSONString(result);
        //操作结束时间
        long end = System.currentTimeMillis();

        //操作耗时
        long costTime = end - begin;
        OperateLog operateLog = new OperateLog(null,operateUser,operateTime,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);

        log.info("AOP记录操作信息:{}",operateLog);

        return result;

    }
}
