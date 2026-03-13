package products.victorian;
import products.Chair;

public class VictorianChair implements Chair {
    @Override
    public void sitOn() { System.out.println("Ngoi tren ghe Co dien: Chan go uon luon, boc nhung."); }
    @Override
    public boolean hasLegs() { return true; }
}