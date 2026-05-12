package com.foodorder.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

public class OrderDTO {

    @Data
    public static class OrderRequest {
        @NotNull(message = "User ID is required")
        private Long userId;

        @NotEmpty(message = "Order items cannot be empty")
        private List<OrderItemRequest> items;

        private String deliveryAddress;
        private String paymentMethod; // COD or BANKING
        private String notes;
    }

    @Data
    public static class OrderItemRequest {
        @NotNull(message = "Food ID is required")
        private Long foodId;

        @NotNull
        @Positive
        private Integer quantity;
    }

    @Data
    public static class OrderResponse {
        private Long id;
        private Long userId;
        private String username;
        private String deliveryAddress;
        private BigDecimal totalAmount;
        private String status;
        private String paymentMethod;
        private String notes;
        private String createdAt;
        private String updatedAt;
        private List<OrderItemResponse> items;
    }

    @Data
    public static class OrderItemResponse {
        private Long foodId;
        private String foodName;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal subtotal;
    }

    @Data
    public static class StatusUpdateRequest {
        private String status;
    }
}
