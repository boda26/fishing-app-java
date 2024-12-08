package com.example.fishinggame.service.impl;

import com.example.fishinggame.mapper.FishCaughtMapper;
import com.example.fishinggame.mapper.FishMapper;
import com.example.fishinggame.mapper.InventoryMapper;
import com.example.fishinggame.model.Fish;
import com.example.fishinggame.model.FishCaught;
import com.example.fishinggame.model.Inventory;
import com.example.fishinggame.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryMapper inventoryMapper;
    private final FishCaughtMapper fishCaughtMapper;
    private final FishMapper fishMapper;

    @Autowired
    public InventoryServiceImpl(InventoryMapper inventoryMapper, FishCaughtMapper fishCaughtMapper, FishMapper fishMapper) {
        this.inventoryMapper = inventoryMapper;
        this.fishCaughtMapper = fishCaughtMapper;
        this.fishMapper = fishMapper;
    }

    @Override
    public Integer createInventory(Inventory inventory) {
        return inventoryMapper.createInventory(inventory);
    }

    @Override
    public Inventory getInventory(Integer id) {
        // Validate input ID
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid inventory ID provided.");
        }

        // Fetch the inventory by ID
        Inventory inventory;
        try {
            inventory = inventoryMapper.getInventory(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch inventory for ID: " + id, e);
        }

        if (inventory == null) {
            throw new IllegalStateException("No inventory found for ID: " + id);
        }

        // Fetch the list of FishCaught associated with the inventory
        List<FishCaught> fishCaughtList;
        try {
            fishCaughtList = fishCaughtMapper.getFishCaughtByInventoryId(inventory.getId());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch fish caught for inventory ID: " + inventory.getId(), e);
        }

        if (fishCaughtList == null || fishCaughtList.isEmpty()) {
            // Set an empty list and return the inventory if no fish are caught
            inventory.setFishCaughtList(List.of());
            return inventory;
        }

        // Collect all fishTypeIds from the FishCaught list
        List<Integer> fishTypeIds = fishCaughtList.stream()
                .map(FishCaught::getFishTypeId)
                .distinct()
                .toList();

        // Batch fetch all Fish objects for the collected fishTypeIds
        List<Fish> fishList;
        try {
            fishList = fishMapper.getFishByIds(fishTypeIds);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch fish details for IDs: " + fishTypeIds, e);
        }

        // Convert the list of Fish to a map for efficient lookups
        Map<Integer, Fish> fishMap;
        try {
            fishMap = fishList.stream()
                    .collect(Collectors.toMap(Fish::getId, fish -> fish));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to map fish details to their IDs.", e);
        }

        // Map the Fish objects to their corresponding FishCaught entries
        for (FishCaught fishCaught : fishCaughtList) {
            Fish fish = fishMap.get(fishCaught.getFishTypeId());
            if (fish != null) {
                fishCaught.setFishType(fish.getType());
            } else {
                // Log a warning or handle the case where a fish is not found
                throw new IllegalStateException("No fish found for fishTypeId: " + fishCaught.getFishTypeId());
            }
        }

        // Set the FishCaught list in the inventory
        inventory.setFishCaughtList(fishCaughtList);
        return inventory;
    }


}
