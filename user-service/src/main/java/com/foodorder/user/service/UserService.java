package com.foodorder.user.service;

import com.foodorder.user.dto.UserDTO;
import com.foodorder.user.entity.User;
import com.foodorder.user.repository.UserRepository;
import com.foodorder.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDTO.AuthResponse register(UserDTO.RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .role(User.Role.USER)
                .active(true)
                .build();

        User saved = userRepository.save(user);
        log.info("✅ New user registered: {} (ID: {})", saved.getUsername(), saved.getId());

        String token = jwtUtil.generateToken(saved.getUsername(), saved.getRole().name(), saved.getId());
        return new UserDTO.AuthResponse(token, toResponse(saved));
    }

    public UserDTO.AuthResponse login(UserDTO.LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUsername()));

        if (!user.isActive()) {
            throw new RuntimeException("Account is disabled");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        log.info("✅ User logged in: {} (Role: {})", user.getUsername(), user.getRole());
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name(), user.getId());
        return new UserDTO.AuthResponse(token, toResponse(user));
    }

    public List<UserDTO.UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserDTO.UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toResponse(user);
    }

    public boolean validateUser(Long userId) {
        return userRepository.findById(userId)
                .map(User::isActive)
                .orElse(false);
    }

    private UserDTO.UserResponse toResponse(User user) {
        UserDTO.UserResponse response = new UserDTO.UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        response.setRole(user.getRole().name());
        response.setActive(user.isActive());
        return response;
    }
}
