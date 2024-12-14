package com.example.fishinggame.service;

import com.example.fishinggame.model.FishCaught;

public interface FishCaughtService {
    FishCaught catchFish(Integer userId);
    Integer deleteFishCaught(Integer fishCaughtId);
    Integer removeFishFromInventory(Integer fishCaughtId);
    Integer addFishToInventory(Integer fishCaughtId, Integer inventoryId);
}
