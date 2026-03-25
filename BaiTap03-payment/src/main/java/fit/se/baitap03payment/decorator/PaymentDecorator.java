package fit.se.baitap03payment.decorator;


import fit.se.baitap03payment.strategy.PaymentStrategy;

public abstract class PaymentDecorator implements PaymentStrategy {
    protected PaymentStrategy wrappedPayment;

    public PaymentDecorator(PaymentStrategy payment) {
        this.wrappedPayment = payment;
    }

    @Override
    public void pay(double amount) {
        wrappedPayment.pay(amount);
    }
}