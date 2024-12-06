package com.example.fishinggame.mapper;

import com.example.fishinggame.model.Fish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FishMapper {

    @Insert("INSERT INTO fish(type, description, status, s_weight, a_weight, b_weight, c_weight, " +
            "mean, standard_deviation, unit_price) VALUES (#{type}, #{description}, #{status}, #{sWeight}, " +
            "#{aWeight}, #{bWeight}, #{cWeight}, #{mean}, #{standardDeviation}, #{unitPrice})")
    Integer createFish(Fish fish);

    @Select("SELECT * FROM fish")
    List<Fish> getAllFish();
}
