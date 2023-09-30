package com.yyandywt99.webserver.service.impl;

import com.yyandywt99.webserver.mapper.userMapper;
import com.yyandywt99.webserver.pojo.user;
import com.yyandywt99.webserver.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-14 16:17
 */
@Service
public class userServiceImpl implements userService {
    @Autowired
    private com.yyandywt99.webserver.mapper.userMapper userMapper;

    @Override
    public List<user> select(){
        List<user> tem = userMapper.select();
        return tem;
    }

    @Override
    public user selectCredit(String name) {
        user tem = userMapper.selectCredit(name);
        return tem;
    }

    @Override
    public String addCredit(Integer id) {
        try {
            userMapper.addCredit(id);
            return "添加成功！";
        } catch (Exception e) {
            e.printStackTrace();
            return "添加成失败";
        }
    }

    @Override
    public String requireUser(user user) {
        try {
            userMapper.requireUser(user);
            return "修改成功！";
        } catch (Exception e) {
            e.printStackTrace();
            return "修改失败";
        }
    }

    @Override
    public String selectImage(String name) {
        try {
            String url = userMapper.selectImage(name);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return "查询失败";
        }
    }

    @Override
    public user selectUser(Integer id) {
        try {
            user res = userMapper.selectUser(id);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public user login(user user) {
        try {
            user res = userMapper.login(user);
            System.out.println(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String adduser(user user) {
        try {
            userMapper.addUser(user);
            return "添加成功！";
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败";
        }
    }


}
