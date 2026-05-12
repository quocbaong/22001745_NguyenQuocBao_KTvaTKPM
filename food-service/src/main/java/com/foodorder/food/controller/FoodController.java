package com.foodorder.food.controller;

import com.foodorder.food.dto.FoodDTO;
import com.foodorder.food.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping
    public ResponseEntity<List<FoodDTO.FoodResponse>> getAllFoods(
            @RequestParam(required = false) String available,
            @RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(foodService.searchFoods(search));
        }
        if ("true".equals(available)) {
            return ResponseEntity.ok(foodService.getAvailableFoods());
        }
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(foodService.getFoodById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createFood(@Valid @RequestBody FoodDTO.FoodRequest request) {
        try {
            FoodDTO.FoodResponse response = foodService.createFood(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(@PathVariable Long id,
                                         @Valid @RequestBody FoodDTO.FoodRequest request) {
        try {
            return ResponseEntity.ok(foodService.updateFood(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {
        try {
            foodService.deleteFood(id);
            return ResponseEntity.ok(Map.of("message", "Food deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "food-service"));
    }
}
