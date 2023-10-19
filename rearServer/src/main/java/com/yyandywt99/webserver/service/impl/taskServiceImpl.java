package com.yyandywt99.webserver.service.impl;

import com.yyandywt99.webserver.mapper.taskMapper;
import com.yyandywt99.webserver.mapper.userMapper;
import com.yyandywt99.webserver.pojo.task;
import com.yyandywt99.webserver.pojo.user;
import com.yyandywt99.webserver.service.taskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-14 16:17
 */
@Service
public class taskServiceImpl implements taskService {
    @Autowired
    private taskMapper taskMapper;

    @Override
    public List<task> selectCheckTask(Integer id,String taskName) {
        List<task> res = taskMapper.selectCheckTask(id,taskName);
        return res;
    }

    @Override
    public String addTask(task task) {
        try {
            taskMapper.addTask(task);
            return "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败";
        }
    }

    @Override
    public String deleteTask(Integer id) {
        try {
            taskMapper.deleteTask(id);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
    }

    @Override
    public String completedTask(Integer id) {
        try {
            taskMapper.completedTask(id);
            taskMapper.updateUserCredit(id);
            return "成功完成任务，你真棒！";
        } catch (Exception e) {
            e.printStackTrace();
            return "完成任务失败";
        }

    }

    @Override
    public task selectTask(Integer id) {
        try {
            task res = taskMapper.selectTask(id);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
