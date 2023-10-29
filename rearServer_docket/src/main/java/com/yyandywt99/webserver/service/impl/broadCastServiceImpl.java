package com.yyandywt99.webserver.service.impl;

import com.yyandywt99.webserver.mapper.broadCastMapper;
import com.yyandywt99.webserver.pojo.broadCast;
import com.yyandywt99.webserver.service.broadCastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-10-22 21:21
 */
@Service
public class broadCastServiceImpl implements broadCastService {
    @Autowired
    private broadCastMapper broadCastMapper;
    @Override
    public List<broadCast> findAllBroadcast() {
        List<broadCast> res = broadCastMapper.findAllBroadcast();
        return res;
    }

    @Override
    public Integer addBroadcast(broadCast broadCast) {
        try {
            broadCastMapper.addBroadcast(broadCast);
            return broadCast.getBroadCastId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String requireBroadcast(broadCast broadCast) {
        try {
            broadCastMapper.requireBroadcast(broadCast);
            return "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "修改失败";
        }
    }

    @Override
    public String deleteBroadcast(Integer id) {
        try {
            broadCastMapper.deleteBroadcast(id);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
    }
}
