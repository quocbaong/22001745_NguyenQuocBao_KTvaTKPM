package org.example.decorator;

import org.example.decorator.OrderService;

public abstract class OrderDecorator implements OrderService {
    protected OrderService service;

    public OrderDecorator(OrderService service) {
        this.service = service;
    }
}