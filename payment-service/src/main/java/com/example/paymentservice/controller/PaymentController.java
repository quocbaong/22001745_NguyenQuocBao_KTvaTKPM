package com.example.paymentservice.controller;

import com.example.paymentservice.dto.PaymentRequestDTO;
import com.example.paymentservice.dto.PaymentResponseDTO;
import com.example.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        try {
            return ResponseEntity.ok(paymentService.processPayment(paymentRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
