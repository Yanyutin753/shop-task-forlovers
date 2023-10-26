package com.yyandywt99.webserver.mapper;

import com.yyandywt99.webserver.pojo.room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-24 20:23
 */
@Mapper
public interface roomMapper {
    public List<room> selectNoRoom(String name);

    public List<room> selectYesRoom(String name);

    public Integer addRoom(room room);

    public void deleteRoom(Integer id);

    public Integer idSelectRoom(Integer id);

    public Integer selectId();

    public void reduceRoom(Integer id);

    @Select("select produceId, founderId, produceName, produceText, " +
            "produceImage, produceNum, checkProduce, checkLove, " +
            "useName, updateTime, registerTime from roomtable " +
            "where produceId = #{id}")
    public room idRoom(Integer id);


    public void updateLoveRoom(Integer id);

    @Update("UPDATE detailusertable " +
            "set allReduceRoom = allReduceRoom + 1 , dayReduceRoom = dayReduceRoom + 1 " +
            "where userId = #{operateUser}")
    public void updateDetailReduceRoom(Integer operateUser);
}
