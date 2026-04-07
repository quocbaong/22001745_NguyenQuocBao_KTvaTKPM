package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentRequestDTO;
import com.example.paymentservice.dto.PaymentResponseDTO;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${service.order.url}")
    private String orderServiceUrl;

    @Value("${service.user.url}")
    private String userServiceUrl;

    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequest) {
        String orderId = paymentRequest.getOrderId();
        String method = paymentRequest.getMethod();

        try {
            // 1. Lấy thông tin Order
            Map order = webClientBuilder.build()
                    .get()
                    .uri(orderServiceUrl + "/" + orderId)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            
            if (order == null) throw new RuntimeException("Không tìm thấy đơn hàng");

            Object userIdObj = order.get("userId");
            String userId = userIdObj != null ? userIdObj.toString() : null;
            Double amount = ((Number) order.get("totalPrice")).doubleValue();

            // 2. Lấy thông tin User để thông báo
            String username = "Unknown User";
            if (userId != null) {
                Map user = webClientBuilder.build()
                        .get()
                        .uri(userServiceUrl + "/" + userId)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();
                if (user != null && user.containsKey("username")) {
                    username = (String) user.get("username");
                }
            }

            // 3. Cập nhật trạng thái Order thành PAID
            webClientBuilder.build()
                    .patch()
                    .uri(orderServiceUrl + "/" + orderId + "/status")
                    .bodyValue(Map.of("status", "PAID"))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            // 4. Lưu lại bản ghi thanh toán
            Payment payment = new Payment(null, orderId, method, amount, "SUCCESS", LocalDateTime.now());
            payment = paymentRepository.save(payment);

            // 5. Gửi thông báo
            String notification = notificationService.sendPaymentNotification(username, orderId);

            return new PaymentResponseDTO("Thanh toán thành công", notification, payment.getId());

        } catch (Exception e) {
            throw new RuntimeException("Lỗi thanh toán: " + e.getMessage());
        }
    }
}
