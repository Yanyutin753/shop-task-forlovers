package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.user;
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
public class userController {
    @Autowired
    private com.yyandywt99.webserver.service.userService userService;

    @GetMapping("user")
    public Result customSelect() {
        List<user> tem = userService.select();
        for (user i : tem) {
            System.out.println(i);
        }
        log.info("查询成功");
        return Result.success(tem);
    }
    @GetMapping("selectUser")
    public Result selectUser(Integer id ) {
        try {
            user tem = userService.selectUser(id);
            log.info("查询成功");
            return Result.success(tem);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("selectCredit")
    public Result selectCredit(@RequestParam("name") String name) {
        user a = userService.selectCredit(name);
        log.info("查询成功");
        return Result.success(a);
    }

    @RequestMapping("increaseCredit")
    public Result increaseCredit(@RequestParam("id") Integer id) {
        userService.addCredit(id);
        log.info("查询成功");
        return Result.success("查询成功");
    }

    @RequestMapping("requireUser")
    public Result requireUser(@RequestBody user user) {
        userService.requireUser(user);
        log.info("修改成功");
        return Result.success("修改成功");
    }

    @RequestMapping("imageUser")
    public Result selectImage(@RequestParam("name") String name) {
        String url = userService.selectImage(name);
        log.info("查询成功");
        return Result.success(url);
    }
    @PostMapping ("addUser")
    public Result addUser(@RequestBody user user) {
        String url = userService.adduser(user);
        log.info("查询成功");
        return Result.success(url);
    }
}
