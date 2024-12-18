package com.example.fishinggame.mapper;

import com.example.fishinggame.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user")
    List<User> getAllUsers();

    @Select("SELECT * FROM user WHERE user_id = #{id}")
    User getUserDetails(Integer id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User getUserByUsername(String username);

    @Insert("INSERT INTO user(username, password, email, phone) " +
            "VALUES (#{username}, #{password}, #{email}, #{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer registerUser(User user);

    @Delete("DELETE FROM user WHERE user_id = #{id}")
    Integer deleteUser(Integer id);

    @Update("UPDATE user SET username = #{username}, password = #{password}, " +
            "email = #{email}, phone = #{phone} WHERE user_id = #{userId}")
    Integer updateUser(User user);

    @Update("UPDATE user SET coins = #{coins} WHERE user_id = #{id}")
    Integer updateCoins(Integer id, Float coins);

    @Select("SELECT coins FROM user WHERE user_id = #{id}")
    Float getCoins(Integer id);

    @Select("SELECT diamonds FROM user WHERE user_id = #{id}")
    Integer getDiamonds(Integer id);

}
