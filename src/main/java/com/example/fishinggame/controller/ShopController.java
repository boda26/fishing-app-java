package com.example.fishinggame.controller;

import com.example.fishinggame.model.Shop;
import com.example.fishinggame.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ResponseEntity<?> getAllShopItems() {
        List<Shop> shopList = shopService.getAllShopItems();
        if (shopList.isEmpty()) {
            return ResponseEntity.ok("The shop is empty!");
        } else {
            return ResponseEntity.ok(shopList);
        }
    }

}
