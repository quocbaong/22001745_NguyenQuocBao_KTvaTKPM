package com.foodorder.food.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

public class FoodDTO {

    @Data
    public static class FoodRequest {
        @NotBlank(message = "Food name is required")
        private String name;

        private String description;

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        private BigDecimal price;

        private String category;
        private String imageUrl;
        private boolean available = true;
    }

    @Data
    public static class FoodResponse {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private String category;
        private String imageUrl;
        private boolean available;
        private String createdAt;
        private String updatedAt;
    }
}
