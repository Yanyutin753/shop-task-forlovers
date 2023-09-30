package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-12 14:03
 */
//输出日志文件
@Slf4j
@RestController
public class taskController {
    @Autowired
    private com.yyandywt99.webserver.service.taskService taskService;

    @GetMapping("selectCheckTask")
    public Result selectCheckTask(@RequestParam("id") Integer id,
    @RequestParam("taskName") String taskName){
        List<task> a = taskService.selectCheckTask(id,taskName);
        log.info("查询成功");
        return Result.success(a);
    }

    @PostMapping("tasks")
    public Result addTask(@RequestBody task task) {
        String result = taskService.addTask(task);
        log.info(result);
        return Result.success(result);
    }

    @DeleteMapping("tasks")
    public Result deleteTask(@RequestParam("id") Integer id){
        String res = taskService.deleteTask(id);
        log.info(res);
        return Result.success(res);
    }

    @PutMapping("tasks")
    public Result completedTask(@RequestParam("id") Integer id){
        String res = taskService.completedTask(id);
        log.info(res);
        return Result.success(res);
    }

    @GetMapping("selectTask")
    public Result selectCheckTask(@RequestParam("id") Integer id){
        task res = taskService.selectTask(id);
        log.info("查询成功");
        return Result.success(res);
    }

}
