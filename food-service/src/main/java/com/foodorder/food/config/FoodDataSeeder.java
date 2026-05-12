package com.foodorder.food.config;

import com.foodorder.food.entity.Food;
import com.foodorder.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class FoodDataSeeder implements CommandLineRunner {

    private final FoodRepository foodRepository;

    @Override
    public void run(String... args) {
        if (foodRepository.count() == 0) {
            foodRepository.save(Food.builder().name("Cơm Tấm Sườn Bì Chả").description("Cơm tấm truyền thống với sườn nướng, bì, chả trứng").price(new BigDecimal("45000")).category("Cơm").imageUrl("https://via.placeholder.com/300x200?text=Com+Tam").available(true).build());
            foodRepository.save(Food.builder().name("Phở Bò Tái").description("Phở bò với thịt tái, nước dùng trong").price(new BigDecimal("55000")).category("Phở").imageUrl("https://via.placeholder.com/300x200?text=Pho+Bo").available(true).build());
            foodRepository.save(Food.builder().name("Bún Bò Huế").description("Bún bò đặc trưng miền Trung, cay nồng").price(new BigDecimal("50000")).category("Bún").imageUrl("https://via.placeholder.com/300x200?text=Bun+Bo+Hue").available(true).build());
            foodRepository.save(Food.builder().name("Bánh Mì Thịt Nguội").description("Bánh mì giòn với thịt nguội, pate, rau").price(new BigDecimal("25000")).category("Bánh Mì").imageUrl("https://via.placeholder.com/300x200?text=Banh+Mi").available(true).build());
            foodRepository.save(Food.builder().name("Gà Rán KFC Style").description("Gà rán giòn kiểu Mỹ, 2 miếng").price(new BigDecimal("65000")).category("Gà").imageUrl("https://via.placeholder.com/300x200?text=Ga+Ran").available(true).build());
            foodRepository.save(Food.builder().name("Pizza Margherita").description("Pizza Italy cổ điển với cà chua và phô mai").price(new BigDecimal("120000")).category("Pizza").imageUrl("https://via.placeholder.com/300x200?text=Pizza").available(true).build());
            foodRepository.save(Food.builder().name("Hamburger Classic").description("Burger bò phô mai với rau tươi").price(new BigDecimal("75000")).category("Burger").imageUrl("https://via.placeholder.com/300x200?text=Burger").available(true).build());
            foodRepository.save(Food.builder().name("Trà Sữa Trân Châu").description("Trà sữa Đài Loan với trân châu đen").price(new BigDecimal("35000")).category("Đồ Uống").imageUrl("https://via.placeholder.com/300x200?text=Tra+Sua").available(true).build());
            foodRepository.save(Food.builder().name("Cà Phê Sữa Đá").description("Cà phê robusta pha phin, uống lạnh").price(new BigDecimal("20000")).category("Đồ Uống").imageUrl("https://via.placeholder.com/300x200?text=Ca+Phe").available(true).build());
            foodRepository.save(Food.builder().name("Chả Giò Chiên").description("Chả giò tôm thịt giòn rụm").price(new BigDecimal("40000")).category("Khai Vị").imageUrl("https://via.placeholder.com/300x200?text=Cha+Gio").available(true).build());

            log.info("✅ Seeded 10 food items successfully");
        }
    }
}
