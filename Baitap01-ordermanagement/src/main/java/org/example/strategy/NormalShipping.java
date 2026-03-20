package org.example.strategy;

public class NormalShipping implements ShippingStrategy {
    @Override
    public void ship() {
        System.out.println(">> Vận chuyển thường");
    }
}