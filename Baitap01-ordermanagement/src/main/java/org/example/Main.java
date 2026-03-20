package org.example;


import org.example.context.OrderContext;
import org.example.decorator.BasicOrderService;
import org.example.decorator.LoggingDecorator;
import org.example.decorator.OrderService;
import org.example.decorator.ValidationDecorator;
import org.example.strategy.FastShipping;
import org.example.strategy.ShippingStrategy;

public class Main {
    public static void main(String[] args) {

        ShippingStrategy shipping = new FastShipping();

        OrderContext order = new OrderContext(shipping);

        OrderService service = new LoggingDecorator(
                new ValidationDecorator(
                        new BasicOrderService()));

        service.process(order);
        service.process(order);
        service.process(order);
    }
}