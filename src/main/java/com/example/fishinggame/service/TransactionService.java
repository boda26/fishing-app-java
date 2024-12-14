package com.example.fishinggame.service;

import com.example.fishinggame.model.FishCaught;

public interface TransactionService {
    Float sellFish(Integer userId, Integer fishId);
    FishCaught buyFish(Integer userId, Integer fishId);
}
