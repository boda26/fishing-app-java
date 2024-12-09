package com.example.fishinggame.service;

import com.example.fishinggame.model.FishCaught;
import com.example.fishinggame.model.Inventory;

public interface InventoryService {
    Integer createInventory(Inventory inventory);
    Inventory getInventoryBasic(Integer id);
    Inventory getInventory(Integer id);
    FishCaught getFishByFishCaughtId(Integer inventoryId, Integer fishCaughtId);
}
