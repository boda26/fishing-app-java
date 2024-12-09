package com.example.fishinggame.service.impl;

import com.example.fishinggame.mapper.ShopMapper;
import com.example.fishinggame.model.Shop;
import com.example.fishinggame.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopMapper shopMapper;

    @Autowired
    public ShopServiceImpl(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    @Override
    public List<Shop> getAllShopItems() {
        return shopMapper.getAllShopItems();
    }

    @Override
    public Integer addToShop(Shop shop) {
        return shopMapper.addToShop(shop);
    }
}
