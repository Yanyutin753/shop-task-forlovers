package com.yyandywt99.webserver.mapper;

import com.yyandywt99.webserver.pojo.OperateLog;
import com.yyandywt99.webserver.pojo.sendDayMessage;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OperateLogMapper {
    //插入日志数据
    @Insert("insert into operate_log (operate_user, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateUser}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insert(OperateLog log);

    @Select("select dayCredit,dayCompleteTask,dayAddTask,dayAddProduce,dayBuyProduce,dayReduceRoom from detailusertable where userId = #{id}")
    public sendDayMessage selectMessage(Integer id);

    @Select("select allCredit as dayCredit," +
            "allCompleteTask as dayCompleteTask," +
            "allAddTask as dayAddTask," +
            "allAddProduce as dayAddProduce, " +
            "allBuyProduce as dayBuyProduce, " +
            "allReduceRoom as dayReduceRoom " +
            "from detailusertable " +
            "where userId = #{id}")
    public sendDayMessage selectAllMessage(Integer id);

    @Update("update detailusertable " +
            "set dayCredit = 0," +
            "dayCompleteTask = 0 ," +
            "dayAddTask = 0," +
            "dayAddProduce = 0," +
            "dayBuyProduce = 0," +
            "dayReduceRoom = 0 " +
            "where userId = #{nameId};")
    public void deleteDayMessage(int nameId);

}
