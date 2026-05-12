package com.foodorder.notification.service;

import com.foodorder.notification.entity.Notification;
import com.foodorder.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification sendNotification(Map<String, Object> payload) {
        Long orderId = payload.get("orderId") != null ? Long.valueOf(payload.get("orderId").toString()) : null;
        Long userId = payload.get("userId") != null ? Long.valueOf(payload.get("userId").toString()) : null;
        String message = payload.getOrDefault("message", "Thông báo từ hệ thống").toString();
        String type = payload.getOrDefault("type", "GENERAL").toString();

        // Console log notification (required)
        log.info("🔔 [NOTIFICATION] {}", message);
        log.info("📧 Gửi thông báo tới User #{}: {}", userId, message);

        // Save to DB
        Notification notification = Notification.builder()
                .orderId(orderId)
                .userId(userId)
                .message(message)
                .type(type)
                .status(Notification.NotificationStatus.SENT)
                .build();

        Notification saved = notificationRepository.save(notification);
        log.info("✅ Notification saved: ID={}, Type={}, Order=#{}", saved.getId(), type, orderId);
        return saved;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getNotificationsByOrderId(Long orderId) {
        return notificationRepository.findByOrderId(orderId);
    }
}
