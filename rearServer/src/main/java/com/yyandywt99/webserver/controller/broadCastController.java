package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.broadCast;
import com.yyandywt99.webserver.service.broadCastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-10-22 21:21
 */
@RestController
public class broadCastController {
    @Autowired
    private broadCastService broadCastService;
    @GetMapping("findAllBroadcast")
    public Result findAllBroadcast(){
        List<broadCast> res = broadCastService.findAllBroadcast();
        return Result.success(res);
    }

    @PostMapping("addBroadcast")
    public Result addBroadcast(@RequestBody broadCast broadCast){
        try {
            Integer res = broadCastService.addBroadcast(broadCast);
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(null);
        }
    }

    @PostMapping ("requireBroadcast")
    public Result requireBroadcast(@RequestBody broadCast broadCast){
        try {
            String res = broadCastService.requireBroadcast(broadCast);
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("修改失败");
        }
    }

    @GetMapping("deleteBroadcast")
    public Result deleteBroadcast(@RequestParam("id") Integer id){
        try {
            String res = broadCastService.deleteBroadcast(id);
            return Result.success(res);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加失败");
        }
    }
}
