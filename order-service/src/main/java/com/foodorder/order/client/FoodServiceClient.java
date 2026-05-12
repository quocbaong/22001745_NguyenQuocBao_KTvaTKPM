package com.foodorder.order.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Component
@Slf4j
public class FoodServiceClient {

    private final RestTemplate restTemplate;
    private final String foodServiceUrl;

    public FoodServiceClient(RestTemplate restTemplate,
                              @Value("${services.food-service-url}") String foodServiceUrl) {
        this.restTemplate = restTemplate;
        this.foodServiceUrl = foodServiceUrl;
    }

    @SuppressWarnings("unchecked")
    public FoodInfo getFoodById(Long foodId) {
        try {
            Map<String, Object> food = restTemplate.getForObject(
                    foodServiceUrl + "/api/foods/" + foodId, Map.class);
            if (food != null) {
                FoodInfo info = new FoodInfo();
                info.setId(Long.valueOf(food.get("id").toString()));
                info.setName(food.get("name").toString());
                info.setPrice(new BigDecimal(food.get("price").toString()));
                info.setAvailable((Boolean) food.get("available"));
                return info;
            }
        } catch (Exception e) {
            log.warn("⚠️ Cannot reach Food Service for food ID {}: {}", foodId, e.getMessage());
        }
        return null;
    }

    @Data
    public static class FoodInfo {
        private Long id;
        private String name;
        private BigDecimal price;
        private boolean available;
    }
}
