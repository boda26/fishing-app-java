package com.example.fishinggame.controller;

import com.example.fishinggame.model.Inventory;
import com.example.fishinggame.model.User;
import com.example.fishinggame.service.InventoryService;
import com.example.fishinggame.service.UserService;
import com.example.fishinggame.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final InventoryService inventoryService;

    @Autowired
    public AuthController(UserService userService, InventoryService inventoryService) {
        this.userService = userService;
        this.inventoryService = inventoryService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User registerRequest) {
        // check if user already exist
        User checkIfExist = userService.getUserByUsername(registerRequest.getUsername());
        if (checkIfExist != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        String hashedPassword = BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt());
        registerRequest.setPassword(hashedPassword);
        Integer registered = userService.registerUser(registerRequest);
        if (registered == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration failed!");
        }

        // create an empty inventory for the new user
        Integer userId = userService.getUserByUsername(registerRequest.getUsername()).getUserId();
        Inventory inventory = new Inventory();
        inventory.setUserId(userId);
        Integer inventoryCreated = inventoryService.createInventory(inventory);
        if (inventoryCreated == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration failed!");
        }
        return ResponseEntity.ok("Successfully registered new user");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password are required");
        }
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
        String token = JwtUtils.generateToken(user.getUsername(), user.getUserId());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    // Static inner class for LoginRequest
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }

    // Response structure for successful login
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class LoginResponse {
        private String token;
    }
}
