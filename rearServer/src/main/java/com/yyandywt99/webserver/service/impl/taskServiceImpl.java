package com.yyandywt99.webserver.service.impl;

import com.yyandywt99.webserver.mapper.taskMapper;
import com.yyandywt99.webserver.mapper.userMapper;
import com.yyandywt99.webserver.pojo.room;
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
    public Integer addTask(task task) {
        try {
            taskMapper.addTask(task);
            Integer res = task.getTaskId();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            taskMapper.updateDetailCredit(id);
            try {
                taskMapper.updateDetailTask(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public String updateLoveTask(Integer id) {
        try {
            taskMapper.updateLoveTask(id);
            return "收藏成功";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void allAddTask(Integer operateUser) {
        try {
            taskMapper.allAddTask(operateUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
