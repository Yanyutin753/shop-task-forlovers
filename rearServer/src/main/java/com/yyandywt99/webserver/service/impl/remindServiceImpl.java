package com.yyandywt99.webserver.service.impl;

import com.yyandywt99.webserver.pojo.room;
import com.yyandywt99.webserver.pojo.task;
import com.yyandywt99.webserver.service.remindService;
import com.yyandywt99.webserver.service.roomService;
import com.yyandywt99.webserver.service.taskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yangyang
 * @create 2023-10-19 16:16
 */
@Service
public class remindServiceImpl implements remindService {
    @Autowired
    private taskService taskService;

    @Autowired
    private roomService roomService;

    @Override
    public task remindTask(Integer id) {
        task task = null;
        try {
            task = taskService.selectTask(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public room remindRoome(Integer id) {
        room room = null;
        try {
            room = roomService.idRoom(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return room;
    }
}
