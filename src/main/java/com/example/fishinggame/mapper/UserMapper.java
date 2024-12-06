package com.example.fishinggame.mapper;

import com.example.fishinggame.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> getAllUsers();

    @Select("SELECT * FROM user WHERE user_id = #{id}")
    User getUserById(int id);

    @Insert("INSERT INTO user(username, password, email, phone) " +
            "VALUES (#{username}, #{password}, #{email}, #{phone})")
    void addUser(User user);

    @Delete("DELETE FROM user WHERE user_id = #{id}")
    int deleteUser(int id);

    @Update("UPDATE user SET username = #{username}, password = #{password}, " +
            "email = #{email}, phone = #{phone} WHERE user_id = #{userId}")
    int updateUser(User user);

}
