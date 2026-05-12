package com.foodorder.inventory.config;

import com.foodorder.inventory.entity.Inventory;
import com.foodorder.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryDataSeeder implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) {
        if (inventoryRepository.count() == 0) {
            // Seeding inventory for the 10 foods in FoodService
            seed(1L, "Cơm Tấm Sườn Bì Chả", 100);
            seed(2L, "Phở Bò Tái", 50);
            seed(3L, "Bún Bò Huế", 60);
            seed(4L, "Bánh Mì Thịt Nguội", 200);
            seed(5L, "Gà Rán KFC Style", 80);
            seed(6L, "Pizza Margherita", 30);
            seed(7L, "Hamburger Classic", 40);
            seed(8L, "Trà Sữa Trân Châu", 150);
            seed(9L, "Cà Phê Sữa Đá", 300);
            seed(10L, "Chả Giò Chiên", 120);

            log.info("✅ Seeded inventory for 10 food items");
        }
    }

    private void seed(Long foodId, String name, int qty) {
        inventoryRepository.save(Inventory.builder()
                .foodId(foodId)
                .foodName(name)
                .quantity(qty)
                .reservedQuantity(0)
                .build());
    }
}
