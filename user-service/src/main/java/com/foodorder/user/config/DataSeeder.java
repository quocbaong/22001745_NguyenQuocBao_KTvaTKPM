package com.foodorder.user.config;

import com.foodorder.user.entity.User;
import com.foodorder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            // Seed admin
            User admin = User.builder()
                    .username("admin")
                    .email("admin@foodorder.com")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("System Administrator")
                    .phone("0900000001")
                    .address("123 Admin Street, HCM City")
                    .role(User.Role.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(admin);

            // Seed regular user
            User user1 = User.builder()
                    .username("user1")
                    .email("user1@foodorder.com")
                    .password(passwordEncoder.encode("user123"))
                    .fullName("Nguyen Van A")
                    .phone("0900000002")
                    .address("456 User Street, HCM City")
                    .role(User.Role.USER)
                    .active(true)
                    .build();
            userRepository.save(user1);

            User user2 = User.builder()
                    .username("user2")
                    .email("user2@foodorder.com")
                    .password(passwordEncoder.encode("user123"))
                    .fullName("Tran Thi B")
                    .phone("0900000003")
                    .address("789 User Street, HCM City")
                    .role(User.Role.USER)
                    .active(true)
                    .build();
            userRepository.save(user2);

            log.info("✅ Seeded users: admin (admin/admin123), user1 (user1/user123), user2 (user2/user123)");
        }
    }
}
