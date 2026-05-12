package com.foodorder.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user-service")
    public ResponseEntity<Map<String, Object>> userServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "status", 503,
            "error", "Service Unavailable",
            "message", "User Service is currently unavailable. Circuit breaker is OPEN. Please try again later.",
            "service", "user-service"
        ));
    }

    @GetMapping("/food-service")
    public ResponseEntity<Map<String, Object>> foodServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "status", 503,
            "error", "Service Unavailable",
            "message", "Food Service is currently unavailable. Circuit breaker is OPEN. Please try again later.",
            "service", "food-service"
        ));
    }

    @GetMapping("/order-service")
    public ResponseEntity<Map<String, Object>> orderServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "status", 503,
            "error", "Service Unavailable",
            "message", "Order Service is currently unavailable. Circuit breaker is OPEN. Please try again later.",
            "service", "order-service"
        ));
    }

    @GetMapping("/payment-service")
    public ResponseEntity<Map<String, Object>> paymentServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "status", 503,
            "error", "Service Unavailable",
            "message", "Payment Service is currently unavailable. Please try again later.",
            "service", "payment-service"
        ));
    }

    @GetMapping("/notification-service")
    public ResponseEntity<Map<String, Object>> notificationServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "status", 503,
            "error", "Service Unavailable",
            "message", "Notification Service is currently unavailable.",
            "service", "notification-service"
        ));
    }

    @GetMapping("/inventory-service")
    public ResponseEntity<Map<String, Object>> inventoryServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "status", 503,
            "error", "Service Unavailable",
            "message", "Inventory Service is currently unavailable.",
            "service", "inventory-service"
        ));
    }

    @GetMapping("/shipping-service")
    public ResponseEntity<Map<String, Object>> shippingServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
            "status", 503,
            "error", "Service Unavailable",
            "message", "Shipping Service is currently unavailable.",
            "service", "shipping-service"
        ));
    }
}
