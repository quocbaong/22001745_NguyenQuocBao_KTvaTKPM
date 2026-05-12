package com.foodorder.shipping.controller;

import com.foodorder.shipping.entity.Shipment;
import com.foodorder.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@RequestBody Map<String, Object> payload) {
        return ResponseEntity.ok(shippingService.createShipment(payload));
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments(
            @RequestParam(required = false) Long userId) {
        if (userId != null) return ResponseEntity.ok(shippingService.getShipmentsByUserId(userId));
        return ResponseEntity.ok(shippingService.getAllShipments());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getShipmentByOrderId(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok(shippingService.getShipmentByOrderId(orderId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                           @RequestBody Map<String, String> payload) {
        try {
            return ResponseEntity.ok(shippingService.updateShippingStatus(id, payload.get("status")));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "shipping-service"));
    }
}
