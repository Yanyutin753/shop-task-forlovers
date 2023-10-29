package com.yyandywt99.webserver.mapper;

import com.yyandywt99.webserver.pojo.broadCast;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-10-22 21:21
 */
@Mapper
public interface broadCastMapper {
    @Select("select broadCastId,broadCastText from homebroadcast ")
    public List<broadCast> findAllBroadcast();

    @Insert("insert into homebroadcast (broadCastText) values (#{broadCastText})")
    @SelectKey(statement = "SELECT last_insert_id() as id ", before = false, resultType = Integer.class, keyProperty = "broadCastId")
    public Integer addBroadcast(broadCast broadCast);

    @Update("update homebroadcast set broadCastText = #{broadCastText} where broadCastId = #{broadCastId}")
    public void requireBroadcast(broadCast broadCast);

    @Delete("delete from homebroadcast where broadCastId = #{id}")
    public void deleteBroadcast(Integer id);
}
