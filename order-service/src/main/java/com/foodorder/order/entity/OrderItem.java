package com.foodorder.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_id", nullable = false)
    private Long foodId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    private Integer quantity;

    @Column(name = "subtotal")
    private BigDecimal subtotal;
}
