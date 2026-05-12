package com.foodorder.order.service;

import com.foodorder.order.client.FoodServiceClient;
import com.foodorder.order.client.UserServiceClient;
import com.foodorder.order.dto.OrderDTO;
import com.foodorder.order.entity.Order;
import com.foodorder.order.entity.OrderItem;
import com.foodorder.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodServiceClient foodServiceClient;
    private final UserServiceClient userServiceClient;

    public OrderDTO.OrderResponse createOrder(OrderDTO.OrderRequest request) {
        // Validate user
        boolean userValid = userServiceClient.validateUser(request.getUserId());
        if (!userValid) {
            throw new RuntimeException("User not found or inactive: " + request.getUserId());
        }

        String username = userServiceClient.getUsernameById(request.getUserId());

        // Build order items
        List<OrderItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderDTO.OrderItemRequest itemReq : request.getItems()) {
            FoodServiceClient.FoodInfo food = foodServiceClient.getFoodById(itemReq.getFoodId());

            String foodName;
            BigDecimal unitPrice;

            if (food != null) {
                if (!food.isAvailable()) {
                    throw new RuntimeException("Food is not available: " + food.getName());
                }
                foodName = food.getName();
                unitPrice = food.getPrice();
            } else {
                // Fallback if food-service is down
                log.warn("⚠️ Food service unavailable, using fallback for food ID: {}", itemReq.getFoodId());
                foodName = "Food #" + itemReq.getFoodId();
                unitPrice = BigDecimal.ZERO;
            }

            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            items.add(OrderItem.builder()
                    .foodId(itemReq.getFoodId())
                    .foodName(foodName)
                    .unitPrice(unitPrice)
                    .quantity(itemReq.getQuantity())
                    .subtotal(subtotal)
                    .build());
        }

        Order order = Order.builder()
                .userId(request.getUserId())
                .username(username)
                .deliveryAddress(request.getDeliveryAddress())
                .totalAmount(totalAmount)
                .paymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "COD")
                .notes(request.getNotes())
                .status(Order.OrderStatus.PENDING)
                .items(items)
                .build();

        Order saved = orderRepository.save(order);
        log.info("✅ Order created: ID {} for user {} (total: {})", saved.getId(), username, totalAmount);
        return toResponse(saved);
    }

    public List<OrderDTO.OrderResponse> getAllOrders() {
        return orderRepository.findByOrderByCreatedAtDesc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrderDTO.OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrderDTO.OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        return toResponse(order);
    }

    public OrderDTO.OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
        try {
            order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
        Order updated = orderRepository.save(order);
        log.info("✅ Order #{} status updated to {}", id, status);
        return toResponse(updated);
    }

    private OrderDTO.OrderResponse toResponse(Order order) {
        OrderDTO.OrderResponse response = new OrderDTO.OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setUsername(order.getUsername());
        response.setDeliveryAddress(order.getDeliveryAddress());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus() != null ? order.getStatus().name() : null);
        response.setPaymentMethod(order.getPaymentMethod());
        response.setNotes(order.getNotes());
        if (order.getCreatedAt() != null) response.setCreatedAt(order.getCreatedAt().toString());
        if (order.getUpdatedAt() != null) response.setUpdatedAt(order.getUpdatedAt().toString());

        if (order.getItems() != null) {
            response.setItems(order.getItems().stream().map(item -> {
                OrderDTO.OrderItemResponse ir = new OrderDTO.OrderItemResponse();
                ir.setFoodId(item.getFoodId());
                ir.setFoodName(item.getFoodName());
                ir.setUnitPrice(item.getUnitPrice());
                ir.setQuantity(item.getQuantity());
                ir.setSubtotal(item.getSubtotal());
                return ir;
            }).collect(Collectors.toList()));
        }
        return response;
    }
}
