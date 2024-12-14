package com.example.fishinggame.controller;

import com.example.fishinggame.model.FishCaught;
import com.example.fishinggame.model.Shop;
import com.example.fishinggame.service.ShopService;
import com.example.fishinggame.service.TransactionService;
import com.example.fishinggame.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService shopService;
    private final TransactionService transactionService;

    @Autowired
    public ShopController(ShopService shopService, TransactionService transactionService) {
        this.shopService = shopService;
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
    public ResponseEntity<?> getAllShopItems() {
        List<Shop> shopList = shopService.getAllShopItems();
        if (shopList.isEmpty()) {
            return ResponseEntity.ok("The shop is empty!");
        } else {
            return ResponseEntity.ok(shopList);
        }
    }

    @PostMapping("/buy/{shopItemId}")
    public ResponseEntity<?> buyFish(@RequestHeader("Authorization") String token, @PathVariable Integer shopItemId) {
        Integer userId = extractUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        try {
            FishCaught fishCaught = transactionService.buyFish(userId, shopItemId);
            return ResponseEntity.ok(fishCaught);
        } catch (IllegalStateException e) {
            // Log the error for debugging
            System.err.println("Validation error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            // Log unexpected runtime exceptions
            System.err.println("Transaction error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transaction failed: " + e.getMessage());
        } catch (Exception e) {
            // Log all other unexpected errors
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


}
