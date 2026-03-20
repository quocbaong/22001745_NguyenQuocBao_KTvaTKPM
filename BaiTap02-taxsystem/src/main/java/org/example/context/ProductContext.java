package org.example.context;


import org.example.state.ProductState;

public class ProductContext {
    private ProductState state;
    private double price;

    public ProductContext(double price, ProductState state) {
        this.price = price;
        this.state = state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public double calculateFinalPrice() {
        return state.applyTax(price);
    }
}