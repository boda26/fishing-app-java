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
        try {
            // Validate input
            if (registerRequest.getUsername() == null || registerRequest.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password are required");
            }

            // Check if user already exists
            User checkIfExist = userService.getUserByUsername(registerRequest.getUsername());
            if (checkIfExist != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }

            // Hash the password and save the user
            String hashedPassword = BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt());
            registerRequest.setPassword(hashedPassword);
            Integer registered = userService.registerUser(registerRequest);

            if (registered == 0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User registration failed due to a system error");
            }

            // Create an empty inventory for the new user
            Inventory inventory = new Inventory();
            inventory.setUserId(registerRequest.getUserId());
            Integer inventoryCreated = inventoryService.createInventory(inventory);

            if (inventoryCreated == 0) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user inventory");
            }

            return ResponseEntity.ok("Successfully registered new user " + registerRequest.getUsername());
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during registration");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Validate input
            if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password are required");
            }

            // Fetch the user by username
            User user = userService.getUserByUsername(loginRequest.getUsername());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
            }

            // Check password
            if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is incorrect!");
            }

            // Generate JWT token
            String token = JwtUtils.generateToken(user.getUsername(), user.getUserId(), user.getIsAdmin());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during login");
        }
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
