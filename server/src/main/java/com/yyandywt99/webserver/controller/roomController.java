package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result nameNoSelectroom(@RequestParam("id") Integer id){
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
    public Result nameNoSelectroom(@RequestParam("name") String name){
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

    @GetMapping("/selectId")
    public Result selectId(){
        try {
            Integer res = roomService.selectId();
            log.info("查询成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("查询失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }

    @PostMapping("/addRoom")
    public Result addNoRoom(@RequestBody room room){
        try {
            String res = roomService.addRoom(room);
            log.info("添加成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("添加失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }

    @DeleteMapping ("/deleteRoom")
    public Result deleteRoom(@RequestParam("id") Integer id){
        try {
            String res = roomService.deleteRoom(id);
            log.info("删除成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("删除失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }

    @PutMapping ("/reduceRoom")
    public Result reduceRoom(@RequestParam("id") Integer id){
        try {
            String res = roomService.reduceRoom(id);
            log.info("减少成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("减少失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
}
