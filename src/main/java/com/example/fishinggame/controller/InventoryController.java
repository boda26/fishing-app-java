package com.example.fishinggame.controller;

import com.example.fishinggame.model.Inventory;
import com.example.fishinggame.service.InventoryService;
import com.example.fishinggame.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
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
