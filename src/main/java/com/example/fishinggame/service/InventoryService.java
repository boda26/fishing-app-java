package com.example.fishinggame.service;

import com.example.fishinggame.model.Inventory;

public interface InventoryService {
    Integer createInventory(Inventory inventory);
    Inventory getInventory(Integer id);
}
