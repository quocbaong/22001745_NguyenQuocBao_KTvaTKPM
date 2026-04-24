package com.iuh.fit.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {

    @KafkaListener(topics = "payment-events", groupId = "notification-group",
            containerFactory = "paymentKafkaListenerContainerFactory")
    public void handlePaymentEvent(PaymentEvent event) {
        if ("PAYMENT_COMPLETED".equals(event.getEventType())) {
            String notification = String.format(
                    "[NOTIFICATION] User '%s' da dat don #%d thanh cong - Phim: '%s' - So tien: %.0f VND",
                    event.getUsername(),
                    event.getBookingId(),
                    event.getMovieTitle(),
                    event.getTotalAmount()
            );
            log.info("=".repeat(60));
            log.info(notification);
            log.info("=".repeat(60));
        } else if ("BOOKING_FAILED".equals(event.getEventType())) {
            String notification = String.format(
                    "[NOTIFICATION] Don dat ve #%d cua user '%s' that bai - Ly do: %s",
                    event.getBookingId(),
                    event.getUsername(),
                    event.getMessage()
            );
            log.warn("=".repeat(60));
            log.warn(notification);
            log.warn("=".repeat(60));
        }
    }
}
