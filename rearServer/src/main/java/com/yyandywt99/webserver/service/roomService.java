package com.yyandywt99.webserver.service;

import com.yyandywt99.webserver.pojo.room;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-24 20:24
 */
public interface roomService {
    public room idRoom(Integer id);

    public Integer idSelectRoom(Integer id);

    public List<room> nameYesSelectRoom(String name);

    public List<room> nameNoSelectRoom(String name);

    public Integer addRoom(room room);

    public String deleteRoom(Integer id);

    public Integer selectId();

    public String reduceRoom(Integer id);

    public String updateLoveRoom(Integer id);

    public void updateDetailReduceRoom(Integer operateUser);
}
