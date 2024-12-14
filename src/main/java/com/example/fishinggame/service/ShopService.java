package com.example.fishinggame.service;

import com.example.fishinggame.model.Shop;

import java.util.List;

public interface ShopService {

    List<Shop> getAllShopItems();
    Integer addToShop(Shop shop);
    Shop getShopItemById(Integer id);
    Integer removeFromShop(Integer id);
}
