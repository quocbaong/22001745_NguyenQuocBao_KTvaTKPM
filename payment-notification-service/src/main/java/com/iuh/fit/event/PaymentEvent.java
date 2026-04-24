package com.iuh.fit.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    private String eventType;   // PAYMENT_COMPLETED or BOOKING_FAILED
    private Long bookingId;
    private Long userId;
    private String username;
    private String movieTitle;
    private Double totalAmount;
    private String message;
    private LocalDateTime timestamp;
}
