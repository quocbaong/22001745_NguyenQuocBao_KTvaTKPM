package com.foodorder.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserDTO {

    @Data
    public static class RegisterRequest {
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50)
        private String username;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        private String fullName;
        private String phone;
        private String address;
    }

    @Data
    public static class LoginRequest {
        @NotBlank(message = "Username is required")
        private String username;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    public static class AuthResponse {
        private String token;
        private String tokenType = "Bearer";
        private UserResponse user;

        public AuthResponse(String token, UserResponse user) {
            this.token = token;
            this.user = user;
        }
    }

    @Data
    public static class UserResponse {
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private String phone;
        private String address;
        private String role;
        private boolean active;
    }
}
