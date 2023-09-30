package com.yyandywt99.webserver.service;

import com.yyandywt99.webserver.pojo.produce;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-23 11:15
 */
public interface produceService {
    public List<produce> nameSelectProduce(String name);

    public List<produce> selectProduce();

    public String updateNum(Integer produceId);

    public String deleteProduce(Integer produceId);

    public String addProduce(produce produce);

    public produce idSelectProduce(Integer id);
}
