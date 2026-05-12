package com.foodorder.shipping.service;

import com.foodorder.shipping.entity.Shipment;
import com.foodorder.shipping.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private final ShipmentRepository shipmentRepository;

    public Shipment createShipment(Map<String, Object> payload) {
        Long orderId = Long.valueOf(payload.get("orderId").toString());
        Long userId = payload.get("userId") != null ? Long.valueOf(payload.get("userId").toString()) : null;
        String address = payload.getOrDefault("deliveryAddress", "").toString();

        // Check if shipment already exists for order
        if (shipmentRepository.findByOrderId(orderId).isPresent()) {
            return shipmentRepository.findByOrderId(orderId).get();
        }

        Shipment shipment = Shipment.builder()
                .orderId(orderId)
                .userId(userId)
                .deliveryAddress(address)
                .status(Shipment.ShippingStatus.PREPARING)
                .build();

        Shipment saved = shipmentRepository.save(shipment);
        log.info("🚚 Shipment created: TRK={}, Order=#{}", saved.getTrackingNumber(), orderId);
        return saved;
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipmentByOrderId(Long orderId) {
        return shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Shipment not found for order: " + orderId));
    }

    public Shipment updateShippingStatus(Long shipmentId, String status) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new RuntimeException("Shipment not found: " + shipmentId));

        Shipment.ShippingStatus newStatus;
        try {
            newStatus = Shipment.ShippingStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid shipping status: " + status);
        }

        shipment.setStatus(newStatus);
        if (newStatus == Shipment.ShippingStatus.DELIVERED) {
            shipment.setActualDelivery(LocalDateTime.now());
        }

        Shipment updated = shipmentRepository.save(shipment);
        log.info("🚚 Shipment #{} status updated to {}", shipmentId, status);
        return updated;
    }

    public List<Shipment> getShipmentsByUserId(Long userId) {
        return shipmentRepository.findByUserId(userId);
    }
}
