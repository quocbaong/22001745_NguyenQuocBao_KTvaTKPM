package com.foodorder.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

public class PaymentDTO {

    @Data
    public static class PaymentRequest {
        @NotNull(message = "Order ID is required")
        private Long orderId;

        private Long userId;

        @NotNull(message = "Amount is required")
        @Positive
        private BigDecimal amount;

        private String paymentMethod; // COD, BANKING, CREDIT_CARD, E_WALLET

        private String notes;
    }

    @Data
    public static class PaymentResponse {
        private Long id;
        private Long orderId;
        private Long userId;
        private BigDecimal amount;
        private String paymentMethod;
        private String status;
        private String transactionId;
        private String notes;
        private String createdAt;
        private String updatedAt;
        private String message;
    }

    @Data
    public static class RefundRequest {
        private String reason;
    }
}
