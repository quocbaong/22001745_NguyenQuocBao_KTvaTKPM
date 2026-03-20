package org.example.context;

import org.example.state.NewState;
import org.example.state.OrderState;
import org.example.strategy.ShippingStrategy;

public class OrderContext {
    private OrderState state;
    private ShippingStrategy shippingStrategy;

    public OrderContext(ShippingStrategy shippingStrategy) {
        this.state = new NewState();
        this.shippingStrategy = shippingStrategy;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public ShippingStrategy getShippingStrategy() {
        return shippingStrategy;
    }

    public void process() {
        state.handle(this);
    }
}