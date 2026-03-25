package fit.se.baitap03payment.state;


import fit.se.baitap03payment.context.PaymentContext;

public interface PaymentState {
    void process(PaymentContext context, double amount);
}