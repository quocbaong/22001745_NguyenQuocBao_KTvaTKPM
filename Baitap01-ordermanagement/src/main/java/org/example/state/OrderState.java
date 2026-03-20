package org.example.state;


import org.example.context.OrderContext;

public interface OrderState {
    void handle(OrderContext context);
}