package com.yyandywt99.webserver.service.impl;

import com.yyandywt99.webserver.mapper.produceMapper;
import com.yyandywt99.webserver.pojo.Result;
import com.yyandywt99.webserver.pojo.produce;
import com.yyandywt99.webserver.service.produceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-23 11:18
 */
@Service
public class produceServiceImpl implements produceService {
    @Autowired
    private produceMapper produceMapper;

    @Override
    public List<produce> nameSelectProduce(String name) {
        List<produce> res = produceMapper.nameSelectProduce(name);
        return res;
    }

    @Override
    public List<produce> selectProduce() {
        List<produce> res = produceMapper.selectProduce();
        return res;
    }

    @Override
    public String updateNum(Integer produceId) {
        try {
            produceMapper.updateNum(produceId);
            produceMapper.updateUserCredit(produceId);
            return "成功购买商品！";
        } catch (Exception e) {
            e.printStackTrace();
            return "购买商品失败";
        }
    }

    @Override
    public String deleteProduce(Integer produceId) {
        try {
            produceMapper.deleteProduce(produceId);
            return "成功删除商品！";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除商品失败";
        }
    }

    @Override
    public String addProduce(produce produce) {
        try {
            produceMapper.addProduce(produce);
            return "成功添加商品！";
        } catch (Exception e) {
            e.printStackTrace();
            return "添加商品失败";
        }
    }

    @Override
    public produce idSelectProduce(Integer id) {
        try {
            produce res = produceMapper.idSelectProduce(id);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
