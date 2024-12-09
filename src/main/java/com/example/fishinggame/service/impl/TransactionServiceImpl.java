package com.example.fishinggame.service.impl;

import com.example.fishinggame.model.FishCaught;
import com.example.fishinggame.model.Inventory;
import com.example.fishinggame.model.Shop;
import com.example.fishinggame.model.User;
import com.example.fishinggame.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final ShopService shopService;
    private final FishCaughtService fishCaughtService;
    private final InventoryService inventoryService;
    private final UserService userService;

    @Autowired
    public TransactionServiceImpl(ShopService shopService, FishCaughtService fishCaughtService,
                                  InventoryService inventoryService, UserService userService) {
        this.shopService = shopService;
        this.fishCaughtService = fishCaughtService;
        this.inventoryService = inventoryService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Float sellFish(Integer userId, Integer fishId) {
        // Step 1: Validate Inventory
        Inventory inventory = inventoryService.getInventoryBasic(userId);
        if (inventory == null) {
            throw new IllegalStateException("Inventory not found for user ID: " + userId);
        }

        // Step 2: Validate Fish in Inventory
        FishCaught fish = inventoryService.getFishByFishCaughtId(inventory.getId(), fishId);
        if (fish == null) {
            throw new IllegalStateException("Fish not found in user's inventory");
        }

        // Step 3: Remove Fish from Inventory
        int removed = fishCaughtService.removeFishFromInventory(fish.getId());
        if (removed == 0) {
            throw new IllegalStateException("Failed to remove fish from inventory");
        }

        // Step 4: Add Fish to Shop
        Shop newShopItem = new Shop();
        newShopItem.setFishCaughtId(fish.getId());
        newShopItem.setFishTypeId(fish.getFishTypeId());
        newShopItem.setRarityLevel(fish.getRarityLevel());
        newShopItem.setWeight(fish.getWeight());
        newShopItem.setPrice(fish.getPrice());
        int addedToShop = shopService.addToShop(newShopItem);
        if (addedToShop == 0) {
            throw new IllegalStateException("Failed to add fish to shop");
        }

        // Step 5: Update User's Coins
        User user = userService.getUserById(userId);
        float updatedCoins = user.getCoins() + fish.getPrice();
        int updated = userService.updateCoins(userId, updatedCoins);
        if (updated == 0) {
            throw new IllegalStateException("Failed to update user's coins");
        }

        return updatedCoins;
    }
}
