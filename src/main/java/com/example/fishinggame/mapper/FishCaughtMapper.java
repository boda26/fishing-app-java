package com.example.fishinggame.mapper;

import com.example.fishinggame.model.FishCaught;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FishCaughtMapper {
    @Select("SELECT * FROM fish_caught WHERE inventory_id = #{inventoryId} ORDER BY fish_caught.price DESC")
    List<FishCaught> getFishCaughtByInventoryId(int inventoryId);

    @Insert("INSERT INTO fish_caught(inventory_id, fish_type_id, weight, rarity_level, price) " +
            "VALUES (#{inventoryId}, #{fishTypeId}, #{weight}, #{rarityLevel}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createFishCaught(FishCaught fishCaught);

    @Select("SELECT * FROM fish_caught WHERE id = #{fishCaughtId}")
    FishCaught getFishCaughtByFishCaughtId(int fishCaughtId);

    @Delete("DELETE FROM fish_caught WHERE id = #{fishCaughtId}")
    Integer deleteFishCaught(Integer fishCaughtId);

    @Update("UPDATE fish_caught SET inventory_id = -1 WHERE id = #{fishCaughtId}")
    Integer removeFishFromInventory(Integer fishCaughtId);

    @Update("UPDATE fish_caught SET inventory_id = #{inventoryId} WHERE id = #{fishCaughtId}")
    Integer addFishToInventory(Integer fishCaughtId, Integer inventoryId);
}
