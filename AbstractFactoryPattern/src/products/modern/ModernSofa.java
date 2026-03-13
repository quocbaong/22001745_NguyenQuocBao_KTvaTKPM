package products.modern;
import products.Sofa;

public class ModernSofa implements Sofa {
    @Override
    public void lieOn() { System.out.println("Nam tren ghe Sofa Hien dai: Chat lieu vai ni xam."); }
    @Override
    public boolean isComfortable() { return true; }
}