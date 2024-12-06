package com.example.fishinggame.mapper;

import com.example.fishinggame.model.FishCaught;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FishCaughtMapper {
    @Select("SELECT * FROM fish_caught where inventory_id = #{inventoryId}")
    List<FishCaught> getFishCaughtByInventoryId(int inventoryId);
}
