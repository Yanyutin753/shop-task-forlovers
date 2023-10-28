package com.yyandywt99.webserver.utils;

/**
 * @author Yangyang
 * @create 2023-10-19 19:22
 */
import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.sendDayMessage;
import com.yyandywt99.webserver.pojo.user;
import com.yyandywt99.webserver.service.WechatNoticeClient;
import com.yyandywt99.webserver.service.operateLogService;
import com.yyandywt99.webserver.service.userService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@RestController
@Slf4j
@Component
@RequestMapping("/api")
public class DailyTask {
    @Autowired
    private userService userService;
    @Autowired
    private operateLogService operateLogService;
    @Autowired
    private WechatNoticeClient wechatNoticeClient;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private TaskScheduler taskScheduler;

    private Map<Integer, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    @GetMapping("openRemind")
    public Result createDynamicScheduledTasks() {
        try {
            String jwt = httpServletRequest.getHeader("Authorization");
            Integer operateUser = null;
            if (jwt != null) {
                jwt = jwt.substring(7);
                Claims claims = JwtUtils.parseJWT(jwt);
                //登陆人ID
                operateUser = (Integer) claims.get("id");
                // 创建并安排每个用户的定时任务
                user res = userService.selectUser(operateUser); // 从数据库获取用户数据
                Runnable task = createTaskForUser(res.getNameId());
                String cronExpression = res.getRemindTime();
                System.out.println(cronExpression);
                ScheduledFuture<?> schedule = taskScheduler.schedule(task, new CronTrigger(cronExpression));
                userService.insertRemind(operateUser);
                scheduledTasks.put(operateUser, schedule);
                return Result.success("开启成功");
            }
            return Result.error("开启失败");
        }
        catch(Exception e){
            e.printStackTrace();
            return Result.error("开启失败");
        }
    }

    @GetMapping("changeRemind")
    public Result changeDynamicScheduledTasks(@RequestParam("time") String time) {
        try {
            String jwt = httpServletRequest.getHeader("Authorization");
            Integer operateUser = null;
            if (jwt != null) {
                jwt = jwt.substring(7);
                Claims claims = JwtUtils.parseJWT(jwt);
                //登陆人ID
                operateUser = (Integer) claims.get("id");
                // 删除
                ScheduledFuture<?> scheduledTask = scheduledTasks.get(operateUser);
                if (scheduledTask != null) {
                    scheduledTask.cancel(true);
                    scheduledTasks.remove(operateUser);
                }

                Runnable task = createTaskForUser(operateUser);
                String cronExpression = time;
                System.out.println(cronExpression);
                ScheduledFuture<?> schedule = taskScheduler.schedule(task, new CronTrigger(cronExpression));

                userService.changeRemind(operateUser,time);
                scheduledTasks.put(operateUser, schedule);
                return Result.success("保存成功");
            }
            return Result.error("保存失败");
        }
        catch(Exception e){
            e.printStackTrace();
            return Result.error("保存失败");
        }
    }

    private Runnable createTaskForUser(int userId) {
        return () -> {
            // 执行相应的任务逻辑
            user tem = userService.selectUser(userId);
            Map<String, Object> sendMap = new HashMap<>();
            //设置消息类型 txt文本
            sendMap.put("msgtype", "text");
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = today.format(formatter);
            sendDayMessage sendMessage = operateLogService.selectMessage(tem.getNameId());
            log.info(sendMessage.toString());
            LocalDate targetDate1 = LocalDate.of(2022, 10, 6);
            LocalDate targetDate2 = LocalDate.of(2022, 9, 9);
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();

            // 使用ChronoUnit计算日期差
            long daysUntilTargetDate1 = ChronoUnit.DAYS.between(targetDate1, currentDate);
            long daysUntilTargetDate2 = ChronoUnit.DAYS.between(targetDate2, currentDate);
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("content", "@"+tem.getName()+
                    "\n\n📅 "+ formattedDate+
                    "\n\n\uD83D\uDC3C\uD83D\uDC3C和\uD83C\uDF8B\uD83C\uDF8B相识："+daysUntilTargetDate1+"天"+
                    "\n\n\uD83D\uDC3C\uD83D\uDC3C和\uD83C\uDF8B\uD83C\uDF8B一起："+daysUntilTargetDate2+"天"+
                    "\n\n💰今日积分数："+tem.getCredit()+
                    "\n\n👑今日获得积分："+sendMessage.getDayCredit()+
                    "\n\n🧾今日任务完成："+sendMessage.getDayCompleteTask()+
                    "\n\n🧩今日添加任务："+sendMessage.getDayAddTask()+
                    "\n\n🎁今日添加商品："+sendMessage.getDayAddProduce()+
                    "\n\n💰今日购买商品："+sendMessage.getDayBuyProduce()+
                    "\n\n🧸今日兑换商品: "+"  "+sendMessage.getDayReduceRoom()+
                    "\n\n"+tem.getRemindText());
            sendMap.put("text", contentMap);
            String NOTICE_KEY = userService.getWechatNoticeKey();
            wechatNoticeClient.sendWechatMsg(NOTICE_KEY, sendMap);
            // 在这里编写每天定时执行的任务逻辑
            log.info("执行了定时任务");
        };
    }


    @GetMapping("closeRemind")
    public Result closeDynamicScheduledTasks() {
        try {
            String jwt = httpServletRequest.getHeader("Authorization");
            Integer operateUser = null;
            if (jwt != null) {
                jwt = jwt.substring(7);
                Claims claims = JwtUtils.parseJWT(jwt);
                operateUser = (Integer) claims.get("id");
                userService.closeRemind(operateUser);
                ScheduledFuture<?> scheduledTask = scheduledTasks.get(operateUser);
                if (scheduledTask != null) {
                    scheduledTask.cancel(true);
                    scheduledTasks.remove(operateUser);
                }
                return Result.success("关闭成功");
            }
            return Result.error("关闭失败");
        }
        catch(Exception e){
            e.printStackTrace();
            return Result.error("开启失败");
        }
}



    @GetMapping("remindUser")
    public Result remindUser(@RequestParam("id") Integer id){
        user tem = userService.selectUser(id);
        Map<String, Object> sendMap = new HashMap<>();
        //设置消息类型 txt文本
        sendMap.put("msgtype", "text");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);
        sendDayMessage sendMessage = operateLogService.selectAllMessage(id);
        log.info(sendMessage.toString());
        LocalDate targetDate1 = LocalDate.of(2022, 10, 6);
        LocalDate targetDate2 = LocalDate.of(2022, 9, 9);
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 使用ChronoUnit计算日期差
        long daysUntilTargetDate1 = ChronoUnit.DAYS.between(targetDate1, currentDate);
        long daysUntilTargetDate2 = ChronoUnit.DAYS.between(targetDate2, currentDate);

        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("content", "@"+tem.getName()+
                "\n\n📅 "+ formattedDate+
                "\n\n\uD83D\uDC3C\uD83D\uDC3C和\uD83C\uDF8B\uD83C\uDF8B相识："+daysUntilTargetDate2+"天"+
                "\n\n\uD83D\uDC3C\uD83D\uDC3C和\uD83C\uDF8B\uD83C\uDF8B一起："+daysUntilTargetDate1+"天"+
                "\n\n💰积分总数："+sendMessage.getDayCredit()+
                "\n\n🧾任务完成总数："+sendMessage.getDayCompleteTask()+
                "\n\n🧩添加任务总数："+sendMessage.getDayAddTask()+
                "\n\n🎁添加商品总数："+sendMessage.getDayAddProduce()+
                "\n\n💰购买商品总数："+sendMessage.getDayBuyProduce()+
                "\n\n🧸兑换商品总数: "+"  "+sendMessage.getDayReduceRoom()+
                "\n\n🥰明天也要继续加油啊，越努力越幸运！加油，奥利给💪💪💪");
        sendMap.put("text", contentMap);
        String NOTICE_KEY = userService.getWechatNoticeKey();
        wechatNoticeClient.sendWechatMsg(NOTICE_KEY, sendMap);
        log.info("执行了定时任务");
        return Result.success("执行了定时任务");
    }

    @GetMapping("selectUserAllRecord")
    public Result selectUserAllRecord(@RequestParam("id") Integer id) {
        try {
            String jwt = httpServletRequest.getHeader("Authorization");
            Integer operateUser = null;
            if (jwt != null) {
                jwt = jwt.substring(7);
                Claims claims = JwtUtils.parseJWT(jwt);
                //登陆人ID
                operateUser = (Integer) claims.get("id");
                if(operateUser == id){
                    sendDayMessage sendMessage = operateLogService.selectAllMessage(id);
                    return Result.success(sendMessage);
                }
                else return Result.error("你不能查询别人的信息！");
            } else return Result.error("not login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("not login");
    }

    @Scheduled(cron = "0 0 0 * * ?") // 每天0点触发
    public void deleteDailyTask() {
        List<user> temUser = userService.select();
        for(user tem : temUser){
            operateLogService.deleteMessage(tem.getNameId());
        }
        log.info("重置任务成功！");
    }


//    @Scheduled(cron = "0 0 23 * * ?") // 每天23点触发
//    public Result executeDailyTask() {
//        List<user> temUser = userService.select();
//        for(user tem : temUser){
//            Map<String, Object> sendMap = new HashMap<>();
//            //设置消息类型 txt文本
//            sendMap.put("msgtype", "text");
//            LocalDate today = LocalDate.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            String formattedDate = today.format(formatter);
//            sendDayMessage sendMessage = operateLogService.selectMessage(tem.getNameId());
//            log.info(sendMessage.toString());
//            LocalDate targetDate1 = LocalDate.of(2022, 10, 6);
//            LocalDate targetDate2 = LocalDate.of(2022, 9, 9);
//            // 获取当前日期
//            LocalDate currentDate = LocalDate.now();
//
//            // 使用ChronoUnit计算日期差
//            long daysUntilTargetDate1 = ChronoUnit.DAYS.between(targetDate1, currentDate);
//            long daysUntilTargetDate2 = ChronoUnit.DAYS.between(targetDate2, currentDate);
//            Map<String, String> contentMap = new HashMap<>();
//            contentMap.put("content", "@"+tem.getName()+
//                                      "\n\n📅 "+ formattedDate+
//                                      "\n\n\uD83D\uDC3C\uD83D\uDC3C和\uD83C\uDF8B\uD83C\uDF8B相识："+daysUntilTargetDate1+"天"+
//                                      "\n\n\uD83D\uDC3C\uD83D\uDC3C和\uD83C\uDF8B\uD83C\uDF8B一起："+daysUntilTargetDate2+"天"+
//                                      "\n\n💰今日积分数："+tem.getCredit()+
//                                      "\n\n👑今日获得积分："+sendMessage.getDayCredit()+
//                                      "\n\n🧾今日任务完成："+sendMessage.getDayCompleteTask()+
//                                      "\n\n🧩今日添加任务："+sendMessage.getDayAddTask()+
//                                      "\n\n🎁今日添加商品："+sendMessage.getDayAddProduce()+
//                                      "\n\n💰今日购买商品："+sendMessage.getDayBuyProduce()+
//                                      "\n\n🧸今日兑换商品: "+"  "+sendMessage.getDayReduceRoom()+
//                                      "\n\n🥰明天也要继续加油啊，越努力越幸运！加油，奥利给💪💪💪");
//            sendMap.put("text", contentMap);
//            wechatNoticeClient.sendWechatMsg(NOTICE_KEY, sendMap);
//        }
//        // 在这里编写每天定时执行的任务逻辑
//        log.info("执行了定时任务");
//        return Result.success("执行了定时任务");
//    }
}

