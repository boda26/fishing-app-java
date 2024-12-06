package com.example.fishinggame.service.impl;

import com.example.fishinggame.mapper.FishCaughtMapper;
import com.example.fishinggame.mapper.InventoryMapper;
import com.example.fishinggame.model.FishCaught;
import com.example.fishinggame.model.Inventory;
import com.example.fishinggame.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryMapper inventoryMapper;
    private final FishCaughtMapper fishCaughtMapper;

    @Autowired
    public InventoryServiceImpl(InventoryMapper inventoryMapper, FishCaughtMapper fishCaughtMapper) {
        this.inventoryMapper = inventoryMapper;
        this.fishCaughtMapper = fishCaughtMapper;
    }

    @Override
    public Integer createInventory(Inventory inventory) {
        return inventoryMapper.createInventory(inventory);
    }

    @Override
    public Inventory getInventory(Integer id) {
        Inventory inventory = inventoryMapper.getInventory(id);
        if (inventory == null) {
            return null;
        }
        int inventoryId = inventory.getId();
        List<FishCaught> fishCaughtList = fishCaughtMapper.getFishCaughtByInventoryId(inventoryId);
        inventory.setFishCaughtList(fishCaughtList);
        return inventory;
    }
}
