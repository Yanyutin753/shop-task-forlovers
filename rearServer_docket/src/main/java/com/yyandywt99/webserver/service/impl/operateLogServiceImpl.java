package com.yyandywt99.webserver.service.impl;

import com.yyandywt99.webserver.mapper.OperateLogMapper;
import com.yyandywt99.webserver.pojo.sendDayMessage;
import com.yyandywt99.webserver.service.operateLogService;
import com.yyandywt99.webserver.utils.DailyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yangyang
 * @create 2023-10-19 19:33
 */
@Service
public class operateLogServiceImpl implements operateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public sendDayMessage selectMessage(Integer id) {
        sendDayMessage res = null;
        try {
            res = operateLogMapper.selectMessage(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public sendDayMessage selectAllMessage(Integer id) {
        sendDayMessage res = null;
        try {
            res = operateLogMapper.selectAllMessage(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void deleteMessage(int nameId) {
        try {
            operateLogMapper.deleteDayMessage(nameId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
