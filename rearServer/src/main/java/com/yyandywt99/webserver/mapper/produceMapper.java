package com.yyandywt99.webserver.mapper;

import com.yyandywt99.webserver.pojo.produce;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-23 11:05
 */
@Mapper
public interface produceMapper {
    public List<produce> nameSelectProduce(String name);

    public List<produce> selectProduce();

    public void updateNum(Integer produceId);

    public void updateUserCredit(Integer produceId);

    public void deleteProduce(Integer produceId);

    public void addProduce(produce produce);

    @Select("select produceId,founderId,produceName, produceText, " +
            "produceImage, produceCredit, produceNum, customer," +
            " updateTime, registerTime from producetable " +
            "where produceId = #{id} ")
    public produce idSelectProduce(Integer id);
}
