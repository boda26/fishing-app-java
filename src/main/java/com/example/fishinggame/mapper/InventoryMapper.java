package com.example.fishinggame.mapper;

import com.example.fishinggame.model.Inventory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InventoryMapper {

    @Insert("INSERT INTO inventory(user_id) VALUES (#{userId})")
    Integer createInventory(Inventory inventory);

    @Select("SELECT * FROM inventory WHERE user_id = #{userId}")
    Inventory getInventory(Integer userId);

}
