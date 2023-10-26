package com.yyandywt99.webserver.aop;

import com.alibaba.fastjson.JSONObject;
import com.yyandywt99.webserver.mapper.OperateLogMapper;
import com.yyandywt99.webserver.pojo.OperateLog;
import com.yyandywt99.webserver.service.WechatNoticeClient;
import com.yyandywt99.webserver.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

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
//    @Autowired
//    private WechatNoticeClient wechatNoticeClient;
//    @Value("${wechat.notice.key}")
//    private String NOTICE_KEY;

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

//        //微信推送信息
//
//        Map<String, Object> sendMap = new HashMap<>();
//        sendMap.put("msgtype", "news");
//        Map<String, Object> contentMap = new HashMap<>();
//        List<Map<String, Object>> list = new ArrayList<>();
//        Map<String, Object> obj = new HashMap<>();
//        String name = new String();
//        String picurl = "http://121.37.243.173/img/home1.148f813b.jpg";
//        String url = "http://121.37.243.173/#/";
//        if(className.contains("taskController")){
//            name = "任务";
//            picurl = "http://121.37.243.173/img/task1.e6e1a2bf.jpg";
//            url = "http://121.37.243.173/#/Task";
//        }
//        else if(className.contains("produceController")){
//            name = "商品";
//            picurl = "http://121.37.243.173/img/produce1.50041cc5.jpg";
//            url = "http://121.37.243.173/#/Produce";
//        }
//        else if(className.contains("roomController")){
//            name = "仓库";
//            picurl = "http://121.37.243.173/img/room1.f1f05f81.jpg";
//            url = "http://121.37.243.173/#/Room";
//        }
//        else if(className.contains("userController")){
//            name = "用户";
//        }
//        obj.put("title", "您的小可爱更新了魔仙堡的"+name+"内容!");
//        obj.put("description", "快去看看吧，记得要努力完成今天任务啊，加油，奥利给！");
//        obj.put("url", url);
//        obj.put("picurl", picurl);
//        list.add(obj);
//        contentMap.put("articles", list);
//        sendMap.put("news", contentMap);
//        wechatNoticeClient.sendWechatMsg(NOTICE_KEY, sendMap);


        OperateLog operateLog = new OperateLog(null,operateUser,operateTime,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);


        log.info("AOP记录操作信息:{}",operateLog);
        return result;

    }
}
