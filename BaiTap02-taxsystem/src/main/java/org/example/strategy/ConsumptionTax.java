package org.example.strategy;

public class ConsumptionTax implements TaxStrategy {
    public double calculate(double price) {
        return price * 0.05;
    }
}