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
public class BookingEvent {
    private String eventType;
    private Long bookingId;
    private Long userId;
    private String username;
    private Long movieId;
    private String movieTitle;
    private Integer numberOfSeats;
    private Double totalAmount;
    private LocalDateTime timestamp;
}
