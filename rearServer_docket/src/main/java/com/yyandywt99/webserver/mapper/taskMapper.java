package com.yyandywt99.webserver.mapper;

import com.yyandywt99.webserver.pojo.task;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-14 10:53
 */
@Mapper
public interface taskMapper {
    public task selectTask(Integer id);

    public List<task> selectCheckTask(Integer id,String taskName);

    public Integer addTask(task tem);

    @Update("UPDATE detailusertable " +
            "set allAddTask = allAddTask + 1 , dayAddTask = dayAddTask + 1 " +
            "where userId = #{operateUser}")
    public void allAddTask(Integer operateUser);

    @Delete("DELETE FROM tasktable where taskId = #{id}")
    public void deleteTask(Integer id);

    public void completedTask(Integer id);

    public void updateUserCredit(Integer id);

    public void updateLoveTask(Integer id);

    public void updateDetailTask(Integer id);

    @Update("UPDATE detailusertable " +
            "set allCredit = allCredit + (SELECT taskCredit FROM tasktable WHERE tasktable.taskId = #{id}), " +
            "dayCredit = dayCredit + (SELECT taskCredit FROM tasktable WHERE tasktable.taskId = #{id}) " +
            "where useName = (select useName from tasktable where taskId = #{id})")
    public void updateDetailCredit(Integer id);
}
