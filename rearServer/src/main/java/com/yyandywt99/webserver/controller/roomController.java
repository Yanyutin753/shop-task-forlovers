package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.anno.Log;
import com.yyandywt99.webserver.mapper.produceMapper;
import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.room;
import com.yyandywt99.webserver.pojo.user;
import com.yyandywt99.webserver.service.taskService;
import com.yyandywt99.webserver.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-24 20:23
 */
@Slf4j
@RestController
public class roomController {
    @Autowired
    private com.yyandywt99.webserver.service.roomService roomService;

    @GetMapping("/idRoom")
    public Result idRoom(@RequestParam("id") Integer id){
        try {
            room res = roomService.idRoom(id);
            log.info("查询成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("查询失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
    @GetMapping("/nameNoSelectRoom")
    public Result nameNoSelectRoom(@RequestParam("name") String name){
        try {
            List<room> res = roomService.nameNoSelectRoom(name);
            log.info("查询成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("查询失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }

    @GetMapping("/nameYesSelectRoom")
    public Result nameSelectroom(@RequestParam("name") String name){
        try {
            List<room> res = roomService.nameYesSelectRoom(name);
            log.info("查询成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("查询失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }

    @GetMapping("/idSelectRoom")
    public Result idSelectroom(@RequestParam("id") Integer id){
        try {
            Integer res = roomService.idSelectRoom(id);
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
    @Log
    @DeleteMapping ("/deleteRoom")
    public Result deleteRoom(@RequestParam("id") Integer id){
        try {
            String jwt = httpServletRequest.getHeader("Authorization");
            Integer operateUser = null;
            if(jwt != null){
                jwt = jwt.substring(7);
                Claims claims = JwtUtils.parseJWT(jwt);
                //登陆人ID
                operateUser = (Integer) claims.get("id");
            }
            room tem = roomService.idRoom(id);
            if(operateUser == tem.getFounderId()) {
                String res = roomService.deleteRoom(id);
                log.info("删除成功");
                return Result.success(res);
            }
            else return Result.error("您不能删除别人的专属商品哦！");
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("删除失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
    @Log
    @PutMapping ("/reduceRoom")
    public Result reduceRoom(@RequestParam("id") Integer id){
        try {
            String jwt = httpServletRequest.getHeader("Authorization");
            Integer operateUser = null;
            if(jwt != null){
                jwt = jwt.substring(7);
                Claims claims = JwtUtils.parseJWT(jwt);
                //登陆人ID
                operateUser = (Integer) claims.get("id");
            }
            roomService.updateDetailReduceRoom(operateUser);
            room tem = roomService.idRoom(id);
            tem.setRegisterTime(LocalDateTime.now());
            if(operateUser == tem.getFounderId()) {
                if(tem.getProduceNum() - 1 <= 0){
                    roomService.deleteRoom(id);
                }
                else{
                    roomService.reduceRoom(id);
                }
                tem.setProduceNum(1);
                tem.setCheckProduce(true);
                log.info(String.valueOf(tem.isCheckLove()));
                Integer s = roomService.addRoom(tem);
                if(s != null){
                    log.info("减少成功");
                }
                return Result.success(s);
            }
            else return Result.error("您不能兑换别人的专属商品哦！");
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("减少失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
    @GetMapping("updateLoveRoom")
    public Result updateLoveTask(@RequestParam("id") Integer id){
        String res = roomService.updateLoveRoom(id);
        log.info("收藏成功");
        return Result.success(res);
    }
}
