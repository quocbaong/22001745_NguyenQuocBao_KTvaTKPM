package org.example.state;


import org.example.context.OrderContext;

public class NewState implements OrderState {
    @Override
    public void handle(OrderContext context) {
        System.out.println("[New] Kiểm tra thông tin đơn hàng...");
        context.setState(new ProcessingState());
    }
}