package com.example.fishinggame.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Inventory {
    private int id;
    private int userId;
    private List<FishCaught> fishCaughtList = new ArrayList<>();
}
