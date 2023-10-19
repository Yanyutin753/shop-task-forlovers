package com.yyandywt99.webserver.service.impl;

import com.yyandywt99.webserver.mapper.roomMapper;
import com.yyandywt99.webserver.pojo.room;
import com.yyandywt99.webserver.service.roomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-24 20:24
 */
@Service
public class roomServiceImpl implements roomService {

    @Autowired
    public roomMapper roomMapper;

    @Override
    public room idRoom(Integer id) {
        try {
            room res = roomMapper.idRoom(id);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer idSelectRoom(Integer id) {
        try {
            Integer res = roomMapper.idSelectRoom(id);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<room> nameYesSelectRoom(String name) {
        try {
            List<room> res = roomMapper.selectYesRoom(name);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<room> nameNoSelectRoom(String name) {
        List<room> res = roomMapper.selectNoRoom(name);
        return res;
    }

    @Override
    public String addRoom(room room) {
        try {
            roomMapper.addRoom(room);
            return "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "添加失败";
    }

    @Override
    public String deleteRoom(Integer id) {
        try {
            roomMapper.deleteRoom(id);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "删除失败";
    }

    @Override
    public Integer selectId() {
        try {
            Integer res = roomMapper.selectId();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String reduceRoom(Integer id) {
        try {
            roomMapper.reduceRoom(id);
            return "减少成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "减少失败";
    }


}
