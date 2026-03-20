package org.example.state;


import org.example.context.OrderContext;

public class DeliveredState implements OrderState {
    @Override
    public void handle(OrderContext context) {
        System.out.println("[Delivered] Đơn hàng đã giao thành công!");
    }
}