package com.example.fishinggame.controller;

import com.example.fishinggame.model.*;
import com.example.fishinggame.service.FishCaughtService;
import com.example.fishinggame.service.InventoryService;
import com.example.fishinggame.service.ShopService;
import com.example.fishinggame.service.UserService;
import com.example.fishinggame.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final FishCaughtService fishCaughtService;
    private final ShopService shopService;
    private final UserService userService;

    @Autowired
    public InventoryController(InventoryService inventoryService, FishCaughtService fishCaughtService,
                               ShopService shopService, UserService userService) {
        this.inventoryService = inventoryService;
        this.fishCaughtService = fishCaughtService;
        this.shopService = shopService;
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

        // remove the fish from user's inventory
        Integer removed = fishCaughtService.removeFishFromInventory(fish.getId());

        // add the fish to the shop
        Shop newShopItem = new Shop();
        newShopItem.setFishCaughtId(fish.getId());
        newShopItem.setFishTypeId(fish.getFishTypeId());
        newShopItem.setRarityLevel(fish.getRarityLevel());
        newShopItem.setWeight(fish.getWeight());
        newShopItem.setPrice(fish.getPrice());
        Integer addedToShop = shopService.addToShop(newShopItem);

        // increment the user's coins
        User user = userService.getUserById(userId);
        Float updatedCoins = user.getCoins() + fish.getPrice();
        userService.updateCoins(userId, updatedCoins);
        return ResponseEntity.ok(updatedCoins);
    }


}
