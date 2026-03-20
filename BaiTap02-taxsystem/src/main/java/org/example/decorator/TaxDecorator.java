package org.example.decorator;


import org.example.strategy.TaxStrategy;

public abstract class TaxDecorator implements TaxStrategy {
    protected TaxStrategy tax;

    public TaxDecorator(TaxStrategy tax) {
        this.tax = tax;
    }
}