package org.example.decorator;


import org.example.strategy.TaxStrategy;

public class AdditionalTax extends TaxDecorator {

    public AdditionalTax(TaxStrategy tax) {
        super(tax);
    }

    public double calculate(double price) {
        // thuế cũ + thêm 5%
        return tax.calculate(price) + price * 0.05;
    }
}