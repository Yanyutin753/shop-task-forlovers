package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.produce;
import com.yyandywt99.webserver.service.produceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-23 11:05
 */
@Slf4j
@RestController
public class produceController {
    @Autowired
    private produceService produceService;

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
    public Result nameSelectProduce(@RequestParam("id") Integer id){
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
    public Result nameSelectProduce(){
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

    @PutMapping("/buyProduce")
    public Result buyProduce(@RequestParam("id") Integer id){
        try {
            String res = produceService.updateNum(id);
            log.info("购买成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("购买失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
    @DeleteMapping("/deleteProduce")
    public Result deleteProduce(@RequestParam("id") Integer id){
        try {
            String res = produceService.deleteProduce(id);
            log.info("删除成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("删除失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }

    @PutMapping("/addProduce")
    public Result addProduce(@RequestBody produce produce){
        try {
            String res = produceService.addProduce(produce);
            log.info("添加成功");
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = e.getMessage(); // 获取异常消息字符串
            log.info("添加失败");
            return Result.error(errorMessage); // 将异常消息放入错误结果
        }
    }
}
