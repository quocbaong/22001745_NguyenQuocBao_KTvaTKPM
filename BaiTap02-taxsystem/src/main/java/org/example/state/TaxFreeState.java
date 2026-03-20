package org.example.state;

import org.example.state.ProductState;

public class TaxFreeState implements ProductState {

    public double applyTax(double price) {
        return price;
    }
}