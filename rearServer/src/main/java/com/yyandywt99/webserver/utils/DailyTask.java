package com.yyandywt99.webserver.utils;

/**
 * @author Yangyang
 * @create 2023-10-19 19:22
 */
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

    public static String getBlessing() {
        String[] blessings = {
                "一愿你此生多福无病灾，二愿你南北虽隔不离散，三愿你郎君如意常相欢。",
                "一愿你平安喜乐，二愿你幸福美满，三愿你快乐无边。",
                "一愿你天天开心，二愿你事事顺心，三愿你岁岁平安。",
                "一愿你笑口常开，二愿你好运连连，三愿你幸福永远。",
                "一愿你心想事成，二愿你事业有成，三愿你幸福美满。",
                "一愿你快乐无边，二愿你幸福永远，三愿你健康平安。",
                "一愿你生活美满，二愿你事业有成，三愿你幸福永远。",
                "一愿你笑口常开，二愿你好运连连，三愿你幸福美满。",
                "一愿你心想事成，二愿你事业有成，三愿你幸福美满。",
                "一愿你快乐无边，二愿你幸福永远，三愿你健康平安。",
                "一愿你生活美满，二愿你事业有成，三愿你幸福永远。",
                "一愿你笑口常开，二愿你好运连连，三愿你幸福美满。",
                "一愿你心想事成，二愿你事业有成，三愿你幸福美满。",
                "一愿你快乐无边，二愿你幸福永远，三愿你健康平安。",
                "一愿你生活美满，二愿你事业有成，三愿你幸福永远。",
                "一愿你笑口常开，二愿你好运连连，三愿你幸福美满。",
                "一愿你心想事成，二愿你事业有成，三愿你幸福美满。",
                "一愿你快乐无边，二愿你幸福永远，三愿你健康平安。",
                "一愿你生活美满，二愿你事业有成，三愿你幸福永远。",
                "一愿你笑口常开，二愿你好运连连，三愿你幸福美满."
        };
        Random random = new Random();
        return blessings[random.nextInt(blessings.length)];
    }

    @GetMapping("weather")
    public List<String> get_weather() {
        List<String> res = null;
        try {
            res = new ArrayList<String>();
            String apiUrl = "http://api.weatherapi.com/v1/forecast.json?key=74a4a61251fa4284a92161943232810&q=xinxiang&days=1&aqi=yes&alerts=yes";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            JsonObject data = JsonParser.parseString(response.toString()).getAsJsonObject();
            // 获取天气信息
            String weather = data.getAsJsonObject("current").getAsJsonObject("condition").get("text").getAsString();
            double temp_c = data.getAsJsonObject("current").get("temp_c").getAsDouble();

            JsonArray forecastDays = data.getAsJsonObject("forecast").getAsJsonArray("forecastday");
            JsonObject forecast = forecastDays.get(0).getAsJsonObject();
            JsonObject day = forecast.getAsJsonObject("day");
            double max_temp = day.get("maxtemp_c").getAsDouble();
            double min_temp = day.get("mintemp_c").getAsDouble();
            res.add(weather);
            res.add(temp_c + "");
            res.add(max_temp + "");
            res.add(min_temp + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

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
            LocalDate targetDate1 = LocalDate.parse(tem.getDisplayDay(), DateTimeFormatter.ISO_LOCAL_DATE);
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            // 使用ChronoUnit计算日期差
            long daysUntilTargetDate1 = ChronoUnit.DAYS.between(targetDate1, currentDate);
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("content", "@"+tem.getName()+
                    "\n🥰亲爱的"+tem.getName()+"宝宝！"+
                    "\n🌹"+getBlessing()+
                    "\n\n📅今天"+ formattedDate+
                    "\n🏫天气情况："+ get_weather().get(0) +
                    "\n"+tem.getDisplayItem()+"："+daysUntilTargetDate1+"天\n💏今天又是爱你的一天😘"+
                    "\n\n👑下面是"+tem.getName()+"专属天气预报☁"+
                    "\n☀今天天气："+get_weather().get(0)+"\n💨平均温度："+get_weather().get(1)+"°C"+
                    "\n🔥最高温度："+get_weather().get(2)+"°C"+
                    "\n❄最低温度："+get_weather().get(3)+"°C"+
                    "\n⭐注意天气变化，及时穿衣👗"+
                    "\n\n🌈下面是"+tem.getName()+"专属任务报告🏆"+
                    "\n💰今日积分数："+tem.getCredit()+
                    "\n✨今日获得积分："+sendMessage.getDayCredit()+
                    "\n🧾今日任务完成："+sendMessage.getDayCompleteTask()+
                    "\n🧩今日添加任务："+sendMessage.getDayAddTask()+
                    "\n🎁今日添加商品："+sendMessage.getDayAddProduce()+
                    "\n💰今日购买商品："+sendMessage.getDayBuyProduce()+
                    "\n🧸今日兑换商品: "+"  "+sendMessage.getDayReduceRoom()+
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

