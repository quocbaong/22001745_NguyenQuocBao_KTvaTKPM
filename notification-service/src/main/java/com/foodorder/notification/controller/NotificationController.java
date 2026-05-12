package com.foodorder.notification.controller;

import com.foodorder.notification.entity.Notification;
import com.foodorder.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> sendNotification(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(notificationService.sendNotification(payload));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long orderId) {
        if (userId != null) return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
        if (orderId != null) return ResponseEntity.ok(notificationService.getNotificationsByOrderId(orderId));
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "notification-service"));
    }
}
