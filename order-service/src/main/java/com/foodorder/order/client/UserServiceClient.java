package com.foodorder.order.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@Slf4j
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    public UserServiceClient(RestTemplate restTemplate,
                              @Value("${services.user-service-url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public boolean validateUser(Long userId) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(
                    userServiceUrl + "/api/users/" + userId + "/validate", Map.class);
            return response != null && Boolean.TRUE.equals(response.get("valid"));
        } catch (Exception e) {
            log.warn("⚠️ Cannot reach User Service for user ID {}: {}. Allowing order.", userId, e.getMessage());
            return true; // fallback: allow order if user-service is down
        }
    }

    @SuppressWarnings("unchecked")
    public String getUsernameById(Long userId) {
        try {
            Map<String, Object> user = restTemplate.getForObject(
                    userServiceUrl + "/api/users/" + userId, Map.class);
            return user != null ? user.get("username").toString() : "Unknown";
        } catch (Exception e) {
            log.warn("⚠️ Cannot get username for user ID {}: {}", userId, e.getMessage());
            return "User#" + userId;
        }
    }
}
