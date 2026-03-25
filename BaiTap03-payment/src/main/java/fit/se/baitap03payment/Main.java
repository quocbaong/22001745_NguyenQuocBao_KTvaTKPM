package fit.se.baitap03payment;

import fit.se.baitap03payment.context.PaymentContext;
import fit.se.baitap03payment.decorator.DiscountDecorator;
import fit.se.baitap03payment.decorator.ProcessingFeeDecorator;
import fit.se.baitap03payment.strategy.CreditCardPayment;
import fit.se.baitap03payment.strategy.PayPalPayment;
import fit.se.baitap03payment.strategy.PaymentStrategy;

public class Main {
    public static void main(String[] args) {
        double amount = 100000;

        // Thanh toán thẻ tín dụng + phí xử lý + giảm giá
        PaymentStrategy creditCard = new CreditCardPayment();
        creditCard = new ProcessingFeeDecorator(creditCard, 5000);
        creditCard = new DiscountDecorator(creditCard, 10000);

        PaymentContext context1 = new PaymentContext(creditCard);
        context1.process(amount);

        System.out.println("-----");

        // Thanh toán PayPal chỉ với phí xử lý
        PaymentStrategy paypal = new PayPalPayment();
        paypal = new ProcessingFeeDecorator(paypal, 3000);

        PaymentContext context2 = new PaymentContext(paypal);
        context2.process(amount);
    }
}