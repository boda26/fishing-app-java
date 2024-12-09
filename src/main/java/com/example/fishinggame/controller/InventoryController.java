package com.example.fishinggame.controller;

import com.example.fishinggame.model.*;
import com.example.fishinggame.service.*;
import com.example.fishinggame.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final TransactionService transactionService;

    @Autowired
    public InventoryController(InventoryService inventoryService, TransactionService transactionService) {
        this.inventoryService = inventoryService;
        this.transactionService = transactionService;
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

    @GetMapping("/{fishId}")
    public ResponseEntity<?> getFishFromInventory(@RequestHeader("Authorization") String token, @PathVariable Integer fishId) {
        // Extract userId from the token
        Integer userId = extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token");
        }

        // check if fish exist in user inventory
        Inventory inventory = inventoryService.getInventoryBasic(userId);
        if (inventory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inventory not found");
        }

        FishCaught fish = inventoryService.getFishByFishCaughtId(inventory.getId(), fishId);
        if (fish == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find this fish in your inventory");
        }
        return ResponseEntity.ok(fish);
    }

    @PostMapping("/sell/{fishId}")
    public ResponseEntity<?> sellFish(@RequestHeader("Authorization") String token, @PathVariable Integer fishId) {
        Integer userId = extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        try {
            Float updatedCoins = transactionService.sellFish(userId, fishId);
            return ResponseEntity.ok("Fish sold successfully. Updated coins: " + updatedCoins);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


}
