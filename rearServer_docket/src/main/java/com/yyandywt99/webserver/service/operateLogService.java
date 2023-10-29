package com.yyandywt99.webserver.service;

import com.yyandywt99.webserver.pojo.sendDayMessage;

/**
 * @author Yangyang
 * @create 2023-10-19 19:26
 */
public interface operateLogService {
    public sendDayMessage selectMessage(Integer id);

    public sendDayMessage selectAllMessage(Integer id);

    public void deleteMessage(int nameId);
}
