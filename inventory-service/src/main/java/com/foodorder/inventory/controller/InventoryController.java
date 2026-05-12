package com.foodorder.inventory.controller;

import com.foodorder.inventory.entity.Inventory;
import com.foodorder.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/food/{foodId}")
    public ResponseEntity<?> getInventoryByFoodId(@PathVariable Long foodId) {
        return inventoryService.getInventoryByFoodId(foodId)
                .map(inv -> ResponseEntity.ok((Object) inv))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/food/{foodId}/status")
    public ResponseEntity<Map<String, Object>> getStockStatus(@PathVariable Long foodId) {
        return ResponseEntity.ok(inventoryService.getStockStatus(foodId));
    }

    @PostMapping
    public ResponseEntity<Inventory> createOrUpdate(@RequestBody Map<String, Object> payload) {
        Long foodId = Long.valueOf(payload.get("foodId").toString());
        String foodName = payload.getOrDefault("foodName", "Unknown").toString();
        Integer quantity = Integer.valueOf(payload.get("quantity").toString());
        return ResponseEntity.ok(inventoryService.createOrUpdateInventory(foodId, foodName, quantity));
    }

    @PostMapping("/deduct")
    public ResponseEntity<Map<String, Object>> deductStock(@RequestBody Map<String, Object> payload) {
        Long foodId = Long.valueOf(payload.get("foodId").toString());
        Integer quantity = Integer.valueOf(payload.get("quantity").toString());
        boolean success = inventoryService.checkAndDeductStock(foodId, quantity);
        return ResponseEntity.ok(Map.of("success", success, "foodId", foodId, "quantity", quantity));
    }

    @PostMapping("/release")
    public ResponseEntity<Map<String, String>> releaseStock(@RequestBody Map<String, Object> payload) {
        Long foodId = Long.valueOf(payload.get("foodId").toString());
        Integer quantity = Integer.valueOf(payload.get("quantity").toString());
        inventoryService.releaseStock(foodId, quantity);
        return ResponseEntity.ok(Map.of("message", "Stock released successfully"));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "inventory-service"));
    }
}
