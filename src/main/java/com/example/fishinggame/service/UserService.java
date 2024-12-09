package com.example.fishinggame.service;

import com.example.fishinggame.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Integer id);
    User getUserByUsername(String username);
    Integer deleteUser(Integer id);
    Integer registerUser(User user);
    Integer updateCoins(Integer id, Float coins);
}
