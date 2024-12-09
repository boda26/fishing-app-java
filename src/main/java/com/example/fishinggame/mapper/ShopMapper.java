package com.example.fishinggame.mapper;

import com.example.fishinggame.model.Shop;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopMapper {

    @Select("SELECT * FROM shop ORDER BY price DESC")
    List<Shop> getAllShopItems();

    @Insert("INSERT INTO shop(fish_caught_id, fish_type_id, rarity_level, weight, price) " +
            "VALUES (#{fishCaughtId}, #{fishTypeId}, #{rarityLevel}, #{weight}, #{price})")
    Integer addToShop(Shop shop);

}
