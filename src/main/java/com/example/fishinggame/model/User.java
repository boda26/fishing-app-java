package com.example.fishinggame.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer level;
    private Integer currentExperience;
    private Integer coins;
    private Integer diamonds;
    private String createdAt;
    private String updatedAt;
}
