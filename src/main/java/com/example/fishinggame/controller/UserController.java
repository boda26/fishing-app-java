package com.example.fishinggame.controller;

import com.example.fishinggame.model.User;
import com.example.fishinggame.service.UserService;
import com.example.fishinggame.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private Integer extractUserIdFromToken(String token) {
        // Extract the token from the Bearer string
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return JwtUtils.extractUserId(token);
        } else {
            return null;
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
        Integer userId = extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        User user = userService.getUserDetails(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @GetMapping("/coins")
    public ResponseEntity<?> getCoins(@RequestHeader("Authorization") String token) {
        Integer userId = extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        Float coins = userService.getCoins(userId);
        if (coins == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You do not have a wallet!");
        }
        return ResponseEntity.ok(coins);
    }

    @GetMapping("/diamonds")
    public ResponseEntity<?> getDiamonds(@RequestHeader("Authorization") String token) {
        Integer userId = extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        Integer diamonds = userService.getDiamonds(userId);
        if (diamonds == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You do not have a wallet!");
        }
        return ResponseEntity.ok(diamonds);
    }

//    @DeleteMapping
//    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
//        // Extract userId from the token
//        Integer userId = extractUserIdFromToken(token);
//        if (userId == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token");
//        }
//        Integer deleted = userService.deleteUser(userId);
//        if (deleted == 0) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//        } else {
//            return ResponseEntity.ok("Successfully deleted user");
//        }
//    }

}
