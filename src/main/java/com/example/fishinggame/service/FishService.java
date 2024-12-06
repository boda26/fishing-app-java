package com.example.fishinggame.service;

import com.example.fishinggame.model.Fish;

import java.util.List;

public interface FishService {
    Integer createFish(Fish fish);
    List<Fish> getAllFish();
}
