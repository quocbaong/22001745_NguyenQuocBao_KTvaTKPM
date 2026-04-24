package com.iuh.fit.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingEventConsumer {

    private final PaymentEventProducer paymentEventProducer;
    private final Random random = new Random();

    @KafkaListener(topics = "booking-events", groupId = "payment-group",
            containerFactory = "bookingKafkaListenerContainerFactory")
    public void handleBookingCreated(BookingEvent event) {
        log.info("[PAYMENT-SERVICE] ====== Received BOOKING_CREATED ======");
        log.info("[PAYMENT-SERVICE] BookingId={}, User='{}', Movie='{}', Seats={}, Amount={}",
                event.getBookingId(), event.getUsername(), event.getMovieTitle(),
                event.getNumberOfSeats(), event.getTotalAmount());
        log.info("[PAYMENT-SERVICE] Processing payment...");

        // Simulate payment processing delay
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 70% success, 30% failure
        boolean isSuccess = random.nextDouble() < 0.7;

        PaymentEvent result;
        if (isSuccess) {
            result = PaymentEvent.builder()
                    .eventType("PAYMENT_COMPLETED")
                    .bookingId(event.getBookingId())
                    .userId(event.getUserId())
                    .username(event.getUsername())
                    .movieTitle(event.getMovieTitle())
                    .totalAmount(event.getTotalAmount())
                    .message("Payment processed successfully")
                    .timestamp(LocalDateTime.now())
                    .build();
            log.info("[PAYMENT-SERVICE] Payment SUCCESS for bookingId={} (70% chance rolled SUCCESS)", event.getBookingId());
        } else {
            result = PaymentEvent.builder()
                    .eventType("BOOKING_FAILED")
                    .bookingId(event.getBookingId())
                    .userId(event.getUserId())
                    .username(event.getUsername())
                    .movieTitle(event.getMovieTitle())
                    .totalAmount(event.getTotalAmount())
                    .message("Payment failed - insufficient funds")
                    .timestamp(LocalDateTime.now())
                    .build();
            log.info("[PAYMENT-SERVICE] Payment FAILED for bookingId={} (30% chance rolled FAILED)", event.getBookingId());
        }

        paymentEventProducer.publishPaymentResult(result);
    }
}
