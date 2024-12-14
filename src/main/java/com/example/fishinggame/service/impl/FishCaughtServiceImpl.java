package com.example.fishinggame.service.impl;

import com.example.fishinggame.mapper.FishCaughtMapper;
import com.example.fishinggame.mapper.FishMapper;
import com.example.fishinggame.mapper.InventoryMapper;
import com.example.fishinggame.model.Fish;
import com.example.fishinggame.model.FishCaught;
import com.example.fishinggame.model.Inventory;
import com.example.fishinggame.service.FishCaughtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FishCaughtServiceImpl implements FishCaughtService {
    private final FishCaughtMapper fishCaughtMapper;
    private final FishMapper fishMapper;
    private final InventoryMapper inventoryMapper;

    @Autowired
    public FishCaughtServiceImpl(FishCaughtMapper fishCaughtMapper, FishMapper fishMapper, InventoryMapper inventoryMapper) {
        this.fishCaughtMapper = fishCaughtMapper;
        this.fishMapper = fishMapper;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public FishCaught catchFish(Integer userId) {
        // Validate user ID
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID provided.");
        }

        // Retrieve the list of all fish
        List<Fish> fishList;
        try {
            fishList = fishMapper.getAllFish();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve the fish list from the database.", e);
        }

        // Ensure the fish list is not empty
        if (fishList == null || fishList.isEmpty()) {
            throw new IllegalStateException("No fish available to catch.");
        }

        // Randomly select a fish type
        int randomIndex = ThreadLocalRandom.current().nextInt(fishList.size());
        Fish fish = fishList.get(randomIndex);

        // Define rarity levels and their respective weights
        List<String> rarityLevels = List.of("S", "A", "B", "C");
        List<Double> weights = List.of(0.05, 0.15, 0.30, 0.50); // S: 5%, A: 15%, B: 30%, C: 50%

        // Generate a random number between 0.0 (inclusive) and 1.0 (exclusive)
        double randomValue = ThreadLocalRandom.current().nextDouble();

        // Select a rarity level based on the random value and weights
        String rarityLevel = "C";
        double cumulativeProbability = 0.0;
        for (int i = 0; i < rarityLevels.size(); i++) {
            cumulativeProbability += weights.get(i);
            if (randomValue < cumulativeProbability) {
                rarityLevel = rarityLevels.get(i);
                break;
            }
        }

        // Determine the weight of the fish based on its rarity
        float weight;
        try {
            if (rarityLevel.equals("S")) {
                weight = (float) ThreadLocalRandom.current().nextDouble(fish.getSWeight(), fish.getSWeight() * 1.3);
            } else if (rarityLevel.equals("A")) {
                weight = (float) ThreadLocalRandom.current().nextDouble(fish.getAWeight(), fish.getSWeight());
            } else if (rarityLevel.equals("B")) {
                weight = (float) ThreadLocalRandom.current().nextDouble(fish.getBWeight(), fish.getAWeight());
            } else {
                weight = (float) ThreadLocalRandom.current().nextDouble(fish.getCWeight(), fish.getBWeight());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to calculate fish weight for rarity level: " + rarityLevel, e);
        }

        // Calculate the price of the fish
        float price = weight * fish.getUnitPrice();

        // Retrieve the user's inventory
        Inventory inventory;
        try {
            inventory = inventoryMapper.getInventory(userId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve the user's inventory for user ID: " + userId, e);
        }

        // Ensure the inventory exists
        if (inventory == null) {
            throw new IllegalStateException("No inventory found for user ID: " + userId);
        }

        // Create and save the FishCaught record
        FishCaught fishCaught = new FishCaught();
        fishCaught.setInventoryId(inventory.getId());
        fishCaught.setFishTypeId(fish.getId());
        fishCaught.setRarityLevel(rarityLevel);
        fishCaught.setWeight(weight);
        fishCaught.setPrice(price);

        try {
            fishCaughtMapper.createFishCaught(fishCaught);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save the caught fish record to the database.", e);
        }
        fishCaught.setFishType(fish.getType());

        // Return the caught fish record
        return fishCaught;
    }

    @Override
    public Integer deleteFishCaught(Integer fishCaughtId) {
        return fishCaughtMapper.deleteFishCaught(fishCaughtId);
    }

    @Override
    public Integer removeFishFromInventory(Integer fishCaughtId) {
        return fishCaughtMapper.removeFishFromInventory(fishCaughtId);
    }

    @Override
    public Integer addFishToInventory(Integer fishCaughtId, Integer inventoryId) {
        return fishCaughtMapper.addFishToInventory(fishCaughtId, inventoryId);
    }


}
