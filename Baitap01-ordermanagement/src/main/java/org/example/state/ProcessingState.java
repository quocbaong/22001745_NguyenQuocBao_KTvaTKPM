package org.example.state;


import org.example.context.OrderContext;

public class ProcessingState implements OrderState {
    @Override
    public void handle(OrderContext context) {
        System.out.println("[Processing] Đóng gói đơn hàng...");
        context.getShippingStrategy().ship();
        context.setState(new DeliveredState());
    }
}