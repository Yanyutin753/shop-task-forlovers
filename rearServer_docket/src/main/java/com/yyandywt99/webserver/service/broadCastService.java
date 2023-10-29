package com.yyandywt99.webserver.service;

import com.yyandywt99.webserver.pojo.broadCast;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-10-22 21:20
 */
public interface broadCastService {
    public List<broadCast> findAllBroadcast();

    public Integer addBroadcast(broadCast broadCast);

    public String requireBroadcast(broadCast broadCast);

    public String deleteBroadcast(Integer id);
}
