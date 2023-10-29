package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.anno.Log;
import com.yyandywt99.webserver.mapper.taskMapper;
import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.produce;
import com.yyandywt99.webserver.pojo.task;
import com.yyandywt99.webserver.pojo.user;
import com.yyandywt99.webserver.service.WechatNoticeClient;
import com.yyandywt99.webserver.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yangyang
 * @create 2023-09-12 14:03
 */
//输出日志文件
@Slf4j
@RestController
@RequestMapping("/api")
public class taskController {
    @Autowired
    private com.yyandywt99.webserver.service.taskService taskService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("selectCheckTask")
    public Result selectCheckTask(@RequestParam("id") Integer id,
    @RequestParam("taskName") String taskName){
        List<task> a = taskService.selectCheckTask(id,taskName);
        log.info("查询成功");
        return Result.success(a);
    }

    @Autowired
    private WechatNoticeClient wechatNoticeClient;
    @Autowired
    private com.yyandywt99.webserver.service.userService userService;

    @Log
    @PostMapping("tasks")
    public Result addTask(@RequestBody task task) {
        String jwt = httpServletRequest.getHeader("Authorization");
        Integer operateUser = null;
        if(jwt != null){
            jwt = jwt.substring(7);
            Claims claims = null;
            try {
                claims = JwtUtils.parseJWT(jwt);
                //登陆人ID
                operateUser = (Integer) claims.get("id");
                taskService.allAddTask(operateUser);
                Integer result = taskService.addTask(task);
                //微信消息
                String picUrl = userService.selectImage(task.getUseName());
                Map<String, Object> sendMap = new HashMap<>();
                sendMap.put("msgtype", "news");
                Map<String, Object> contentMap = new HashMap<>();
                List<Map<String, Object>> list = new ArrayList<>();
                Map<String, Object> obj = new HashMap<>();
                String baseUrl = httpServletRequest.getRequestURL().toString();
                String requestUri = httpServletRequest.getRequestURI();
                String baseUrlWithoutPath = baseUrl.replace(requestUri, "");
                log.info(baseUrlWithoutPath);
                String url = baseUrlWithoutPath+"/#/TaskDisplay/" + result;
                obj.put("title", "🥰"+ task.getUseName() +"有新的任务:"+task.getTaskName()+",快去看看完成吧！");
                obj.put("description","🌈"+ task.getTaskText()+"快去完成任务吧，记得一定要完成今日任务啊，加油，奥利给！");
                obj.put("url",url);
                obj.put("picurl",picUrl);
                list.add(obj);
                contentMap.put("articles", list);
                sendMap.put("news", contentMap);
                String NOTICE_KEY = userService.getWechatNoticeKey();
                wechatNoticeClient.sendWechatMsg(NOTICE_KEY, sendMap);
                return Result.success("添加成功");
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("not_login");
            }
        }
        else return Result.error("not_login");
    }

    @Log
    @DeleteMapping("tasks")
    public Result deleteTask(@RequestParam("id") Integer id){
        String jwt = httpServletRequest.getHeader("Authorization");
        Integer operateUser = null;
        if(jwt != null){
            jwt = jwt.substring(7);
            Claims claims = JwtUtils.parseJWT(jwt);
            //登陆人ID
            operateUser = (Integer) claims.get("id");
        }
        task tem = taskService.selectTask(id);
        if(operateUser == tem.getFounderId()) {
            String res = taskService.deleteTask(id);
            log.info(res);
            return Result.success(res);
        }
        else return Result.error("你不能删除别的用户任务信息！");
    }

    @Log
    @PutMapping("tasks")
    public Result completedTask(@RequestParam("id") Integer id){
        String jwt = httpServletRequest.getHeader("Authorization");
        Integer operateUser = null;
        if(jwt != null){
            jwt = jwt.substring(7);
            Claims claims = JwtUtils.parseJWT(jwt);
            //登陆人ID
            operateUser = (Integer) claims.get("id");
        }
        task task = taskService.selectTask(id);
        if(operateUser == task.getFounderId()) {
            String res = taskService.completedTask(id);
            log.info(res);
            //微信消息
            //第一条
            Map<String, Object> firstsendMap = new HashMap<>();
            //设置消息类型 txt文本
            firstsendMap.put("msgtype", "text");
            Map<String, String> firstContentMap = new HashMap<>();
            firstContentMap.put("content", "@"+task.getUseName());
            firstsendMap.put("text", firstContentMap);
            String NOTICE_KEY = userService.getWechatNoticeKey();
            wechatNoticeClient.sendWechatMsg(NOTICE_KEY, firstsendMap);

            //第二条
            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("msgtype", "news");
            Map<String, Object> contentMap = new HashMap<>();
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> obj = new HashMap<>();
            String picUrl = userService.selectImage(task.getUseName());
            String baseUrl = httpServletRequest.getRequestURL().toString();
            String requestUri = httpServletRequest.getRequestURI();
            String baseUrlWithoutPath = baseUrl.replace(requestUri, "");
            log.info(baseUrlWithoutPath);
            String url = baseUrlWithoutPath+"/#/wechatTaskDisplay/" + id;
            obj.put("title", "🥰恭喜"+ task.getUseName() +"宝宝完成"+task.getTaskName()+"任务,继续加油哇");
            obj.put("description","🌈"+ task.getTaskText()+"快去完成其他任务吧，记得一定要完成今日任务啊，加油，奥利给！");
            obj.put("url",url);
            obj.put("picurl",picUrl);
            list.add(obj);
            contentMap.put("articles", list);
            sendMap.put("news", contentMap);
            wechatNoticeClient.sendWechatMsg(NOTICE_KEY, sendMap);

            //返回
            return Result.success(res);
        }
        else return Result.error("你不能完成别的用户布置的任务！");
    }

    @GetMapping("selectTask")
    public Result selectCheckTask(@RequestParam("id") Integer id){
        task res = taskService.selectTask(id);
        log.info("查询成功");
        return Result.success(res);
    }

    @GetMapping("updateLoveTask")
    public Result updateLoveTask(@RequestParam("id") Integer id){
        String res = taskService.updateLoveTask(id);
        log.info("收藏成功");
        return Result.success(res);
    }

}
