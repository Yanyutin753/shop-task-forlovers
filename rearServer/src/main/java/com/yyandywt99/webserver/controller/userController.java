package com.yyandywt99.webserver.controller;

import com.yyandywt99.webserver.anno.Log;
import com.yyandywt99.webserver.anno.limitLog;
import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.user;
import com.yyandywt99.webserver.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public Result selectUser() {
        List<user> tem = userService.select();
        for (user i : tem) {
            System.out.println(i);
        }
        log.info("查询成功");
        return Result.success(tem);
    }

    @limitLog
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

    @Log
    @RequestMapping("increaseCredit")
    public Result increaseCredit(@RequestParam("id") Integer id) {
        userService.addCredit(id);
        log.info("添加成功");
        return Result.success("添加成功");
    }

    @Log
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

    @Log
    @PostMapping ("addUser")
    public Result addUser(@RequestBody user user) {
        String url = userService.adduser(user);
        log.info("添加成功");
        return Result.success(url);
    }

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Log
    @PutMapping ("deleteUser")
    public Result deleteUser(@RequestParam("id") Integer id) {
        String jwt = httpServletRequest.getHeader("Authorization");
        Integer operateUser = null;
        if(jwt != null){
            jwt = jwt.substring(7);
            Claims claims = JwtUtils.parseJWT(jwt);
            //登陆人ID
            operateUser = (Integer) claims.get("id");
        }
        if(operateUser == id){
            String url = userService.deleteUser(id);
            log.info("删除成功");
            return Result.success(url);
        }
        else return Result.error("你不能删除别的用户！");
    }
}
