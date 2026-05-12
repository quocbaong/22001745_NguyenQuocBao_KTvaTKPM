package com.foodorder.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_id", unique = true, nullable = false)
    private Long foodId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity = 0;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Integer getAvailableQuantity() {
        return quantity - (reservedQuantity != null ? reservedQuantity : 0);
    }
}
