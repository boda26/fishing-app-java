package com.example.fishinggame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Fish {
    private int id;
    private String type;
    private String description;
    private boolean status;

    @JsonProperty("sWeight")
    private Float sWeight;

    @JsonProperty("aWeight")
    private Float aWeight;

    @JsonProperty("bWeight")
    private Float bWeight;

    @JsonProperty("cWeight")
    private Float cWeight;

    private Float mean;
    private Float standardDeviation;
    private Float unitPrice;
}