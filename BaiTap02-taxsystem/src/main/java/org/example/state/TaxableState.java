package org.example.state;


import org.example.strategy.TaxStrategy;

public class TaxableState implements ProductState {

    private TaxStrategy taxStrategy;

    public TaxableState(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }

    public double applyTax(double price) {
        return price + taxStrategy.calculate(price);
    }
}