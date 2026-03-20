package org.example.decorator;


import org.example.context.OrderContext;

public class BasicOrderService implements OrderService {
    @Override
    public void process(OrderContext context) {
        context.process();
    }
}