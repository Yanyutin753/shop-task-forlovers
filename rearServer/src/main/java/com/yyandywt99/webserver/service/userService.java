package com.yyandywt99.webserver.service;

import com.yyandywt99.webserver.pojo.user;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-14 16:20
 */
public interface userService {
    public List<user> select();

    public user selectCredit(String name);

    public String addCredit(Integer id);

    public String requireUser(user user);

    public String selectImage(String name);

    public user selectUser(Integer id);

    public user login(user user);

    public String adduser(user user);

    public String deleteUser(Integer id);
}
