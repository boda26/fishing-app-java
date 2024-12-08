package com.example.fishinggame.controller;

import com.example.fishinggame.model.FishCaught;
import com.example.fishinggame.service.FishCaughtService;
import com.example.fishinggame.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fish-caught")
public class FishCaughtController {

    private final FishCaughtService fishCaughtService;

    @Autowired
    public FishCaughtController(FishCaughtService fishCaughtService) {
        this.fishCaughtService = fishCaughtService;
    }

    private Integer extractUserIdFromToken(String token) {
        // Extract the token from the Bearer string
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return JwtUtils.extractUserId(token);
        } else {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<?> catchFish(@RequestHeader("Authorization") String token) {
        try {
            // Extract the user ID from the token
            Integer userId = extractUserIdFromToken(token);

            // Call the service to catch fish
            FishCaught fishCaught = fishCaughtService.catchFish(userId);

            // Return the caught fish with a 200 OK status
            return ResponseEntity.ok(fishCaught);
        } catch (IllegalArgumentException e) {
            // Handle invalid input (e.g., missing or invalid user ID)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            // Handle logical errors (e.g., no inventory found)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            // Handle other runtime errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        } catch (Exception e) {
            // Catch-all for any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


}
