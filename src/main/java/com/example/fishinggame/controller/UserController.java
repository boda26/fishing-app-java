package com.example.fishinggame.controller;

import com.example.fishinggame.model.Inventory;
import com.example.fishinggame.model.User;
import com.example.fishinggame.service.InventoryService;
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
    private final InventoryService inventoryService;

    @Autowired
    public UserController(UserService userService, InventoryService inventoryService) {
        this.userService = userService;
        this.inventoryService = inventoryService;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        User user =  userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Integer userId = extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token");
        }
        Integer deleted = userService.deleteUser(userId);
        if (deleted == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            return ResponseEntity.ok("Successfully deleted user");
        }
    }

    @GetMapping("/inventory")
    public ResponseEntity<?> getInventory(@RequestHeader("Authorization") String token) {
        // Extract userId from the token
        Integer userId = extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token");
        }

        Inventory inventory = inventoryService.getInventory(userId);
        if (inventory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory not found");
        } else {
            return ResponseEntity.ok(inventory);
        }
    }

}
