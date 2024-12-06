package com.example.fishinggame.controller;

import com.example.fishinggame.model.Fish;
import com.example.fishinggame.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fish")
public class FishController {

    private final FishService fishService;

    @Autowired
    public FishController(FishService fishService) {
        this.fishService = fishService;
    }

    @PostMapping
    public ResponseEntity<?> createFish(@RequestBody Fish fish) {
        Integer fishCreated = fishService.createFish(fish);
        if (fishCreated == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fish creation failed");
        } else {
            return ResponseEntity.ok("Successfully created fish " + fish.getType());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllFish() {
        List<Fish> fishList = fishService.getAllFish();
        if (fishList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No fish found");
        } else {
            return ResponseEntity.ok(fishList);
        }
    }
}
