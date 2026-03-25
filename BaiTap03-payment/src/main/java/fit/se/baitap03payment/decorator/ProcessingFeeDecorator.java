package fit.se.baitap03payment.decorator;


import fit.se.baitap03payment.strategy.PaymentStrategy;

public class ProcessingFeeDecorator extends PaymentDecorator {
    private double fee;

    public ProcessingFeeDecorator(PaymentStrategy payment, double fee) {
        super(payment);
        this.fee = fee;
    }

    @Override
    public void pay(double amount) {
        double total = amount + fee;
        System.out.println("Thêm phí xử lý: " + fee + " VND");
        super.pay(total);
    }
}