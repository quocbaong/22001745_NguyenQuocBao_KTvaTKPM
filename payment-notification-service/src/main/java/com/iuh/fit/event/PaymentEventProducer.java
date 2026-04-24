package com.iuh.fit.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private static final String TOPIC = "payment-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentResult(PaymentEvent event) {
        log.info("[PAYMENT-SERVICE] Publishing {} for bookingId={}", event.getEventType(), event.getBookingId());
        kafkaTemplate.send(TOPIC, String.valueOf(event.getBookingId()), event);
        log.info("[PAYMENT-SERVICE] Event published to topic '{}'", TOPIC);
    }
}
