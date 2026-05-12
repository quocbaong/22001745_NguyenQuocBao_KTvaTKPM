package com.foodorder.food.service;

import com.foodorder.food.dto.FoodDTO;
import com.foodorder.food.entity.Food;
import com.foodorder.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {

    private final FoodRepository foodRepository;

    public List<FoodDTO.FoodResponse> getAllFoods() {
        return foodRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<FoodDTO.FoodResponse> getAvailableFoods() {
        return foodRepository.findByAvailableTrue().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public FoodDTO.FoodResponse getFoodById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
        return toResponse(food);
    }

    public FoodDTO.FoodResponse createFood(FoodDTO.FoodRequest request) {
        Food food = Food.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .imageUrl(request.getImageUrl())
                .available(request.isAvailable())
                .build();
        Food saved = foodRepository.save(food);
        log.info("✅ Food created: {} (ID: {})", saved.getName(), saved.getId());
        return toResponse(saved);
    }

    public FoodDTO.FoodResponse updateFood(Long id, FoodDTO.FoodRequest request) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setCategory(request.getCategory());
        food.setImageUrl(request.getImageUrl());
        food.setAvailable(request.isAvailable());
        Food updated = foodRepository.save(food);
        log.info("✅ Food updated: {} (ID: {})", updated.getName(), updated.getId());
        return toResponse(updated);
    }

    public void deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new RuntimeException("Food not found with id: " + id);
        }
        foodRepository.deleteById(id);
        log.info("✅ Food deleted: ID {}", id);
    }

    public List<FoodDTO.FoodResponse> searchFoods(String keyword) {
        return foodRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private FoodDTO.FoodResponse toResponse(Food food) {
        FoodDTO.FoodResponse response = new FoodDTO.FoodResponse();
        response.setId(food.getId());
        response.setName(food.getName());
        response.setDescription(food.getDescription());
        response.setPrice(food.getPrice());
        response.setCategory(food.getCategory());
        response.setImageUrl(food.getImageUrl());
        response.setAvailable(food.isAvailable());
        if (food.getCreatedAt() != null) response.setCreatedAt(food.getCreatedAt().toString());
        if (food.getUpdatedAt() != null) response.setUpdatedAt(food.getUpdatedAt().toString());
        return response;
    }
}
