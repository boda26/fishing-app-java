package com.example.fishinggame.service;

import com.example.fishinggame.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Integer id);
    Integer deleteUser(Integer id);
    Integer registerUser(User user);
}
