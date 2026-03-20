package org.example.state;


import org.example.context.OrderContext;

public class CancelledState implements OrderState {
    @Override
    public void handle(OrderContext context) {
        System.out.println("[Cancelled] Hủy đơn và hoàn tiền!");
    }
}