package com.example.paymentservice.service;

import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
public class NotificationService {
    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    public String sendPaymentNotification(String username, String orderId) {
        String message = String.format("User %s đã đặt đơn #%s thành công", username, orderId);
        logger.info("NOTIFICATION: " + message);
        return message;
    }
}
