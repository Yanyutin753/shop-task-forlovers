package com.yyandywt99.webserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.user;
import com.yyandywt99.webserver.service.userService;
import com.yyandywt99.webserver.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yangyang
 * @create 2023-09-27 15:13
 */
@Slf4j
@RestController
@RequestMapping("/api")

public class loginController {
    @Autowired
    private com.yyandywt99.webserver.service.userService userService;


    @PostMapping("/login")
    public Result login(@RequestBody user user) {
        user res = userService.login(user);
        if(res != null){
            log.info("登录成功");
            Map<String,Object> chaims = new HashMap<String,Object>();
            chaims.put("id",res.getNameId());
            System.out.println(res.getNameId());
            String s = JwtUtils.generateJwt(chaims);
            return Result.success(s);
        }
        return Result.error("登陆失败");
    }

    @PostMapping("/loginToken")
    public Result loginToken(@RequestParam("token") String token){
        log.info(token);
        if(!StringUtils.hasLength(token)){
            log.info("请求头token为空,返回未登录的信息");
            return Result.error("NOT_LOGIN");
        }
        try {
            JwtUtils.parseJWT(token);
            log.info("令牌合法，可以正常登录");
            return Result.success("YES_LOGIN");
        } catch (Exception e) {//jwt解析失败
            e.printStackTrace();
            log.info("解析令牌失败, 返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            return error;
        }
    }
}
