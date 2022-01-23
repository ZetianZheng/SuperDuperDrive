package com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.mapper;

import com.udacity.jwdnd.course1.cloudStorage.SuperDuperDrive.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * MyBaits Mapper,it help map your java object（User POJO) to the queries data by JDBC
 */
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
}
