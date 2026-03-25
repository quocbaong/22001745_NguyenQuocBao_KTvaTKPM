package fit.se.baitap03payment.state;


import fit.se.baitap03payment.context.PaymentContext;

public class CompletedState implements PaymentState {
    @Override
    public void process(PaymentContext context, double amount) {
        System.out.println("Trạng thái: Hoàn tất giao dịch với số tiền " + amount + " VND");
    }
}