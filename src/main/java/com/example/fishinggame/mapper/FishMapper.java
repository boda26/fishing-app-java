package com.example.fishinggame.mapper;

import com.example.fishinggame.model.Fish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    @Select("<script>" +
            "SELECT id, type FROM fish WHERE id IN " +
            "<foreach item='id' collection='list' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<Fish> getFishByIds(@Param("list") List<Integer> ids);

}
