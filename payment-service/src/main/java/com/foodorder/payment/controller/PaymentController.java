package com.foodorder.payment.controller;

import com.foodorder.payment.dto.PaymentDTO;
import com.foodorder.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> processPayment(@Valid @RequestBody PaymentDTO.PaymentRequest request) {
        try {
            PaymentDTO.PaymentResponse response = paymentService.processPayment(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO.PaymentResponse>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getPaymentByOrderId(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/order/{orderId}/complete")
    public ResponseEntity<?> completePayment(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok(paymentService.completePaymentByOrderId(orderId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<?> refundPayment(@PathVariable Long id,
                                            @RequestBody PaymentDTO.RefundRequest request) {
        try {
            return ResponseEntity.ok(paymentService.refundPayment(id, request.getReason()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "payment-service"));
    }
}
