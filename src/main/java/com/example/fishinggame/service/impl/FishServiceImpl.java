package com.example.fishinggame.service.impl;

import com.example.fishinggame.mapper.FishMapper;
import com.example.fishinggame.model.Fish;
import com.example.fishinggame.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FishServiceImpl implements FishService {

    private final FishMapper fishMapper;

    @Autowired
    public FishServiceImpl(FishMapper fishMapper) {
        this.fishMapper = fishMapper;
    }

    @Override
    public Integer createFish(Fish fish) {
        return fishMapper.createFish(fish);
    }

    @Override
    public List<Fish> getAllFish() {
        return fishMapper.getAllFish();
    }
}
