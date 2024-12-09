package com.example.fishinggame.service.impl;

import com.example.fishinggame.mapper.UserMapper;
import com.example.fishinggame.model.User;
import com.example.fishinggame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public User getUserDetails(Integer id) {
        return userMapper.getUserDetails(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public Integer deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public Integer registerUser(User user) {
        return userMapper.registerUser(user);
    }

    @Override
    public Integer updateCoins(Integer id, Float coins) {
        return userMapper.updateCoins(id, coins);
    }

    @Override
    public Float getCoins(Integer id) {
        return userMapper.getCoins(id);
    }

    @Override
    public Integer getDiamonds(Integer id) {
        return userMapper.getDiamonds(id);
    }
}
