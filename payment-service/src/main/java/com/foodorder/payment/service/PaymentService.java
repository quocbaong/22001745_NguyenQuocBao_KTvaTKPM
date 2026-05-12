package com.foodorder.payment.service;

import com.foodorder.payment.dto.PaymentDTO;
import com.foodorder.payment.entity.Payment;
import com.foodorder.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Value("${services.order-service-url}")
    private String orderServiceUrl;

    @Value("${services.notification-service-url}")
    private String notificationServiceUrl;

    @Value("${services.shipping-service-url}")
    private String shippingServiceUrl;

    public PaymentDTO.PaymentResponse processPayment(PaymentDTO.PaymentRequest request) {
        // Check if payment already exists for this order
        if (paymentRepository.findByOrderId(request.getOrderId()).isPresent()) {
            throw new RuntimeException("Payment already processed for order: " + request.getOrderId());
        }

        // Simulate payment processing
        Payment.PaymentMethod method;
        try {
            method = Payment.PaymentMethod.valueOf(
                    request.getPaymentMethod() != null ? request.getPaymentMethod().toUpperCase() : "COD");
        } catch (IllegalArgumentException e) {
            method = Payment.PaymentMethod.COD;
        }

        // Simulate payment delay for BANKING
        if (method == Payment.PaymentMethod.BANKING) {
            log.info("💳 Processing BANKING payment for order #{} ...", request.getOrderId());
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .paymentMethod(method)
                .status(method == Payment.PaymentMethod.COD ? Payment.PaymentStatus.PENDING : Payment.PaymentStatus.COMPLETED)
                .notes(request.getNotes())
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("✅ Payment recorded ({}): TXN={}, Order=#{}, Amount={}, Method={}",
                saved.getStatus(), saved.getTransactionId(), saved.getOrderId(), saved.getAmount(), saved.getPaymentMethod());

        // Update order status: PAID for Banking, CONFIRMED for COD
        String newOrderStatus = (method == Payment.PaymentMethod.COD) ? "CONFIRMED" : "PAID";
        updateOrderStatus(request.getOrderId(), newOrderStatus);

        // Send notification
        sendNotification(request.getOrderId(), request.getUserId(), saved.getTransactionId());

        // Create shipping record
        createShipping(request.getOrderId(), request.getUserId());

        PaymentDTO.PaymentResponse response = toResponse(saved);
        response.setMessage("Thanh toán thành công! Đơn hàng của bạn đang được xử lý.");
        return response;
    }

    public PaymentDTO.PaymentResponse completePaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order: " + orderId));

        if (payment.getStatus() == Payment.PaymentStatus.PENDING) {
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
            Payment updated = paymentRepository.save(payment);
            
            // Update order status to PAID
            updateOrderStatus(orderId, "PAID");
            
            log.info("✅ COD Payment finalized for order #{}", orderId);
            return toResponse(updated);
        }
        return toResponse(payment);
    }

    public PaymentDTO.PaymentResponse refundPayment(Long paymentId, String reason) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));

        if (payment.getStatus() != Payment.PaymentStatus.COMPLETED) {
            throw new RuntimeException("Cannot refund payment with status: " + payment.getStatus());
        }

        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        payment.setNotes("Refund: " + reason);
        Payment updated = paymentRepository.save(payment);

        // Update order status
        updateOrderStatus(payment.getOrderId(), "CANCELLED");

        log.info("💰 Payment refunded: TXN={}, Order=#{}", payment.getTransactionId(), payment.getOrderId());

        PaymentDTO.PaymentResponse response = toResponse(updated);
        response.setMessage("Hoàn tiền thành công!");
        return response;
    }

    public List<PaymentDTO.PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PaymentDTO.PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for order: " + orderId));
        return toResponse(payment);
    }

    private void updateOrderStatus(Long orderId, String status) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("status", status);
            restTemplate.put(orderServiceUrl + "/api/orders/" + orderId + "/status", body);
            log.info("📦 Order #{} status updated to {}", orderId, status);
        } catch (Exception e) {
            log.warn("⚠️ Could not update order status: {}", e.getMessage());
        }
    }

    private void sendNotification(Long orderId, Long userId, String transactionId) {
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("orderId", orderId);
            notification.put("userId", userId);
            notification.put("transactionId", transactionId);
            notification.put("message", "User #" + userId + " đã đặt đơn #" + orderId + " thành công");
            notification.put("type", "ORDER_PAID");
            restTemplate.postForObject(notificationServiceUrl + "/api/notifications", notification, Map.class);
            log.info("🔔 Notification sent for order #{}", orderId);
        } catch (Exception e) {
            log.warn("⚠️ Could not send notification: {}", e.getMessage());
            // Log to console as fallback
            log.info("📢 [NOTIFICATION] User #{} đã đặt đơn #{} thành công!", userId, orderId);
        }
    }

    private void createShipping(Long orderId, Long userId) {
        try {
            Map<String, Object> shipping = new HashMap<>();
            shipping.put("orderId", orderId);
            shipping.put("userId", userId);
            shipping.put("status", "PREPARING");
            restTemplate.postForObject(shippingServiceUrl + "/api/shipping", shipping, Map.class);
            log.info("🚚 Shipping record created for order #{}", orderId);
        } catch (Exception e) {
            log.warn("⚠️ Could not create shipping record: {}", e.getMessage());
        }
    }

    private PaymentDTO.PaymentResponse toResponse(Payment payment) {
        PaymentDTO.PaymentResponse response = new PaymentDTO.PaymentResponse();
        response.setId(payment.getId());
        response.setOrderId(payment.getOrderId());
        response.setUserId(payment.getUserId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod() != null ? payment.getPaymentMethod().name() : null);
        response.setStatus(payment.getStatus() != null ? payment.getStatus().name() : null);
        response.setTransactionId(payment.getTransactionId());
        response.setNotes(payment.getNotes());
        if (payment.getCreatedAt() != null) response.setCreatedAt(payment.getCreatedAt().toString());
        if (payment.getUpdatedAt() != null) response.setUpdatedAt(payment.getUpdatedAt().toString());
        return response;
    }
}
