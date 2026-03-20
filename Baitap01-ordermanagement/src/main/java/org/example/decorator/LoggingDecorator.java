package org.example.decorator;


import org.example.context.OrderContext;

public class LoggingDecorator extends OrderDecorator {

    public LoggingDecorator(OrderService service) {
        super(service);
    }

    @Override
    public void process(OrderContext context) {
        System.out.println("LOG: Bắt đầu xử lý đơn hàng...");
        service.process(context);
        System.out.println("LOG: Kết thúc xử lý.");
    }
}