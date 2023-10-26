package com.yyandywt99.webserver.service;

import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.room;
import com.yyandywt99.webserver.pojo.task;

/**
 * @author Yangyang
 * @create 2023-10-19 16:15
 */
public interface remindService {
    public task remindTask(Integer id);

    public room remindRoome(Integer id);
}
