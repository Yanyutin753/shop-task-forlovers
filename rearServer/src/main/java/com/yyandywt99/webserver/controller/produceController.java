package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.anno.Log;
import com.yyandywt99.webserver.mapper.produceMapper;
import com.yyandywt99.webserver.pojo.*;
import com.yyandywt99.webserver.service.WechatNoticeClient;
import com.yyandywt99.webserver.service.produceService;
import com.yyandywt99.webserver.service.roomService;
import com.yyandywt99.webserver.service.taskService;
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
 * @create 2023-09-23 11:05
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class produceController {

    @Autowired
    private produceService produceService;

    @Autowired
    private roomService roomService;

    @GetMapping("/nameSelectProduce")
    public Result nameSelectProduce(@RequestParam("name") String name){
        try {
            List<produce> res = produceService.nameSelectProduce(name);
            log.info("查询成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("查询失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }

    @GetMapping("/idSelectProduce")
    public Result idSelectProduce(@RequestParam("id") Integer id){
        try {
            produce res = produceService.idSelectProduce(id);
            log.info("查询成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("查询失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }

    @GetMapping("/selectProduce")
    public Result SelectProduce(){
        try {
            List<produce> res = produceService.selectProduce();
            log.info("查询成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("查询失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private com.yyandywt99.webserver.service.userService userService;
    @Autowired
    private produceMapper produceMapper;
    @Log
    @PutMapping("/buyProduce")
    public Result buyProduce(@RequestParam("id") Integer id){
        try {
            String jwt = httpServletRequest.getHeader("Authorization");
            Integer operateUser = null;
            if(jwt != null){
                jwt = jwt.substring(7);
                Claims claims = JwtUtils.parseJWT(jwt);
                //登陆人ID
                operateUser = (Integer) claims.get("id");
            }
            produce tem = produceService.idSelectProduce(id);
//            客户的类
            user a = userService.selectCredit(tem.getCustomer());
            room room = new room();
            room.setFounderId(a.getNameId());
            room.setProduceName(tem.getProduceName());
            room.setProduceText(tem.getProduceText());
            room.setProduceImage(tem.getProduceImage());
            room.setRegisterTime(tem.getRegisterTime());
            room.setCheckLove(tem.isCheckLove());
            room.setProduceNum(1);
            room.setCheckProduce(false);
            room.setUseName(tem.getCustomer());
            user user = userService.selectUser(operateUser);
            if(user.getName().equals(tem.getCustomer())) {
                produceService.updateDetailBuyProduce(a.getNameId());
                produceService.updateNum(id);
                produce res = produceService.idSelectProduce(id);
                if(res.getProduceNum() <= 0){
                    produceService.deleteProduce(id);
                }
                Integer s = roomService.addRoom(room);
                log.info(s.toString());
                log.info("购买成功");
                Map<String, Object> sendMap = new HashMap<>();
                sendMap.put("msgtype", "news");
                Map<String, Object> contentMap = new HashMap<>();
                List<Map<String, Object>> list = new ArrayList<>();
                String baseUrl = httpServletRequest.getRequestURL().toString();
                String requestUri = httpServletRequest.getRequestURI();
                String baseUrlWithoutPath = baseUrl.replace(requestUri, "");
                log.info(baseUrlWithoutPath);
                Map<String, Object> obj = new HashMap<>();
                String url = baseUrlWithoutPath+"/#/RoomDisplay/" + room.getProduceId();
                obj.put("title", "🥰您的宝宝" + user.getName() + "提醒您兑换" + room.getProduceName() + "商品"+",快去看看吧！");
                obj.put("description", "🌈快给你的宝宝兑换礼物吧，同时也要记得努力完成今日任务，加油，奥利给！");
                obj.put("url", url);
                obj.put("picurl", room.getProduceImage());
                list.add(obj);
                contentMap.put("articles", list);
                sendMap.put("news", contentMap);
                String NOTICE_KEY = userService.getWechatNoticeKey();
                wechatNoticeClient.sendWechatMsg(NOTICE_KEY, sendMap);
                return Result.success(s);
            }
            else return Result.error("您不能购买别人的专属商品哦！");
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("购买失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
    @Log
    @DeleteMapping("/deleteProduce")
    public Result deleteProduce(@RequestParam("id") Integer id){
        try {
            String jwt = httpServletRequest.getHeader("Authorization");
            Integer operateUser = null;
            if(jwt != null){
                jwt = jwt.substring(7);
                Claims claims = JwtUtils.parseJWT(jwt);
                //登陆人ID
                operateUser = (Integer) claims.get("id");
            }
            produce tem = produceService.idSelectProduce(id);
            if(operateUser == tem.getFounderId()) {
                String res = produceService.deleteProduce(id);
                log.info("删除成功");
                return Result.success(res);
            }
            else return Result.error("您不能删除别人设置的专属商品哦！");
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("删除失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
    @Autowired
    private WechatNoticeClient wechatNoticeClient;
    @Log
    @PutMapping("/addProduce")
    public Result addProduce(@RequestBody produce produce){
        try {
            String jwt = httpServletRequest.getHeader("Authorization");
            Integer operateUser = null;
            if(jwt != null){
                jwt = jwt.substring(7);
                Claims claims = JwtUtils.parseJWT(jwt);
                //登陆人ID
                operateUser = (Integer) claims.get("id");
            }
            Integer res = produceService.addProduce(produce);
            produceService.updateDetailAddProduce(operateUser);
            log.info("添加成功");
            Map<String, Object> sendMap = new HashMap<>();
            sendMap.put("msgtype", "news");
            Map<String, Object> contentMap = new HashMap<>();
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> obj = new HashMap<>();
            String baseUrl = httpServletRequest.getRequestURL().toString();
            String requestUri = httpServletRequest.getRequestURI();
            String baseUrlWithoutPath = baseUrl.replace(requestUri, "");
            log.info(baseUrlWithoutPath);
            String url = baseUrlWithoutPath+"/#/wechatProduceDisplay/"+res;
            obj.put("title", "🧸"+produce.getCustomer()+"有新的商品:"+produce.getProduceName()+",快去看看购买吧！");
            obj.put("description", "🎁"+produce.getProduceText()+"快去完成任务吧，攒积分购买吧！");
            obj.put("url",url);
            obj.put("picurl", produce.getProduceImage());
            list.add(obj);
            contentMap.put("articles", list);
            sendMap.put("news", contentMap);
            String NOTICE_KEY = userService.getWechatNoticeKey();
            wechatNoticeClient.sendWechatMsg(NOTICE_KEY, sendMap);
            log.info("添加成功");
            return Result.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("添加失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
    @GetMapping("updateLoveProduce")
    public Result updateLoveProduce(@RequestParam("id") Integer id){
        String res = produceService.updateLoveProduce(id);
        log.info("收藏成功");
        return Result.success(res);
    }
}
