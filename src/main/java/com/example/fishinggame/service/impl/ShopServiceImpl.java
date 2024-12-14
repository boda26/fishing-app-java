package com.example.fishinggame.service.impl;

import com.example.fishinggame.mapper.FishMapper;
import com.example.fishinggame.mapper.ShopMapper;
import com.example.fishinggame.model.Fish;
import com.example.fishinggame.model.FishCaught;
import com.example.fishinggame.model.Shop;
import com.example.fishinggame.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopMapper shopMapper;
    private final FishMapper fishMapper;

    @Autowired
    public ShopServiceImpl(ShopMapper shopMapper, FishMapper fishMapper) {
        this.shopMapper = shopMapper;
        this.fishMapper = fishMapper;
    }

    @Override
    public List<Shop> getAllShopItems() {
        List<Shop> shopItems = shopMapper.getAllShopItems();

        // Collect all fishTypeIds from the FishCaught list
        List<Integer> fishTypeIds = shopItems.stream()
                .map(Shop::getFishTypeId)
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
        for (Shop shopItem: shopItems) {
            Fish fish = fishMap.get(shopItem.getFishTypeId());
            if (fish != null) {
                shopItem.setFishTypeName(fish.getType());
            } else {
                // Log a warning or handle the case where a fish is not found
                throw new IllegalStateException("No fish found for fishTypeId: " + shopItem.getFishTypeId());
            }
        }
        return shopItems;
    }

    @Override
    public Integer addToShop(Shop shop) {
        return shopMapper.addToShop(shop);
    }

    @Override
    public Shop getShopItemById(Integer id) {
        return shopMapper.getShopItemById(id);
    }

    @Override
    public Integer removeFromShop(Integer id) {
        return shopMapper.removeFromShop(id);
    }
}
