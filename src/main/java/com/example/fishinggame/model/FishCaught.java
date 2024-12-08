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
public class FishCaught {
    private int id;
    private int inventoryId;
    private int fishTypeId;
    private String fishType;
    private float weight;
    private String rarityLevel;
    private LocalDateTime caughtAt;
    private float price;
}
