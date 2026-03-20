package org.example.decorator;


import org.example.context.OrderContext;

public class ValidationDecorator extends OrderDecorator {

    public ValidationDecorator(OrderService service) {
        super(service);
    }

    @Override
    public void process(OrderContext context) {
        System.out.println("VALIDATE: Kiểm tra dữ liệu hợp lệ...");
        service.process(context);
    }
}