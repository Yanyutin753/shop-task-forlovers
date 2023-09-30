package com.yyandywt99.webserver.service;

import com.yyandywt99.webserver.pojo.task;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-14 16:19
 */
public interface taskService {
    public List<task> selectCheckTask(Integer id,String taskName);

    public String addTask(task tem);

    public String deleteTask(Integer id);

    public String completedTask(Integer id);

    public task selectTask(Integer id);
}
