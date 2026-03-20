package org.example.decorator;


import org.example.context.OrderContext;

public interface OrderService {
    void process(OrderContext context);
}