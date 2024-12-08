package com.example.fishinggame.mapper;

import com.example.fishinggame.model.FishCaught;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FishCaughtMapper {
    @Select("SELECT * FROM fish_caught where inventory_id = #{inventoryId}")
    List<FishCaught> getFishCaughtByInventoryId(int inventoryId);

    @Insert("INSERT INTO fish_caught(inventory_id, fish_type_id, weight, rarity_level, price) " +
            "VALUES (#{inventoryId}, #{fishTypeId}, #{weight}, #{rarityLevel}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer createFishCaught(FishCaught fishCaught);
}
