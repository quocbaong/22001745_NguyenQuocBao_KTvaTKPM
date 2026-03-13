package products.modern;
import products.Chair;

public class ModernChair implements Chair {
    @Override
    public void sitOn() { System.out.println("Ngoi tren ghe Hien dai: Rat thoai mai va toi gian."); }
    @Override
    public boolean hasLegs() { return true; }
}