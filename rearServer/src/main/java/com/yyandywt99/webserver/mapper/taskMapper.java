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

    public void addTask(task tem);

    @Delete("DELETE FROM tasktable where taskId = #{id}")
    public void deleteTask(Integer id);

    public void completedTask(Integer id);

    public void updateUserCredit(Integer id);
}
