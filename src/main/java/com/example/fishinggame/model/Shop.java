package com.example.fishinggame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Shop {
    private int id;
    private int fishCaughtId;
    private int fishTypeId;
    private String fishTypeName;
    private String rarityLevel;
    private float weight;
    private float price;
    private LocalDateTime soldTime;
}
