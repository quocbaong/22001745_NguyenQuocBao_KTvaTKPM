package com.foodorder.inventory.service;

import com.foodorder.inventory.entity.Inventory;
import com.foodorder.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryByFoodId(Long foodId) {
        return inventoryRepository.findByFoodId(foodId);
    }

    public Inventory createOrUpdateInventory(Long foodId, String foodName, Integer quantity) {
        Inventory inventory = inventoryRepository.findByFoodId(foodId)
                .orElse(Inventory.builder().foodId(foodId).foodName(foodName).reservedQuantity(0).build());
        inventory.setQuantity(quantity);
        return inventoryRepository.save(inventory);
    }

    public boolean checkAndDeductStock(Long foodId, Integer quantity) {
        Optional<Inventory> optInv = inventoryRepository.findByFoodId(foodId);
        if (optInv.isEmpty()) {
            log.warn("⚠️ Inventory not found for food ID: {}. Allowing order.", foodId);
            return true; // fallback: allow
        }
        Inventory inv = optInv.get();
        if (inv.getAvailableQuantity() < quantity) {
            log.warn("⚠️ Insufficient stock for food ID: {}. Available: {}, Requested: {}",
                    foodId, inv.getAvailableQuantity(), quantity);
            return false;
        }
        inv.setReservedQuantity(inv.getReservedQuantity() + quantity);
        inventoryRepository.save(inv);
        log.info("✅ Stock reserved: Food #{}, Quantity: {}", foodId, quantity);
        return true;
    }

    public void releaseStock(Long foodId, Integer quantity) {
        inventoryRepository.findByFoodId(foodId).ifPresent(inv -> {
            int newReserved = Math.max(0, inv.getReservedQuantity() - quantity);
            inv.setReservedQuantity(newReserved);
            inventoryRepository.save(inv);
            log.info("✅ Stock released: Food #{}, Quantity: {}", foodId, quantity);
        });
    }

    public Map<String, Object> getStockStatus(Long foodId) {
        return inventoryRepository.findByFoodId(foodId)
                .map(inv -> Map.<String, Object>of(
                        "foodId", inv.getFoodId(),
                        "foodName", inv.getFoodName(),
                        "quantity", inv.getQuantity(),
                        "reservedQuantity", inv.getReservedQuantity(),
                        "availableQuantity", inv.getAvailableQuantity()
                ))
                .orElse(Map.of("foodId", foodId, "message", "Not tracked"));
    }
}
