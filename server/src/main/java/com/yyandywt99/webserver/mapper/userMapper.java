package com.yyandywt99.webserver.mapper;

import com.yyandywt99.webserver.pojo.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Yangyang
 * @create 2023-09-12 14:00
 */
@Mapper
public interface userMapper {
//  按ids查找用户
    public List<user> select();

    @Select("select nameId, name, userText, userImage, credit, " +
            "updateTime, registerTime from usertable " +
            "where nameId = #{id}"
    )
    public user selectUser(Integer id);

    //按用户name查找用户信息，返回credit
    public user selectCredit(String name);

    public void addCredit(Integer id);

    public void requireUser(user user);

    @Select("select userImage from usertable where name = #{name}")
    public String selectImage(String name);

    public user login(user user);

    public void addUser(user user);
}

