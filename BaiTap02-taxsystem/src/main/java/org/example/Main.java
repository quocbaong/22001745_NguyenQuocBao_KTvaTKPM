package org.example;


import org.example.context.ProductContext;
import org.example.decorator.AdditionalTax;
import org.example.state.TaxFreeState;
import org.example.state.TaxableState;
import org.example.strategy.TaxStrategy;
import org.example.strategy.VATTax;

public class Main {
    public static void main(String[] args) {

        double price = 100;

        TaxStrategy tax = new AdditionalTax(new VATTax());

        ProductContext product = new ProductContext(price, new TaxableState(tax));

        System.out.println("Giá cuối (có thuế): " + product.calculateFinalPrice());

        product.setState(new TaxFreeState());

        System.out.println("Giá cuối (miễn thuế): " + product.calculateFinalPrice());
    }
}