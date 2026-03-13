package products.modern;
import products.CoffeeTable;

public class ModernCoffeeTable implements CoffeeTable {
    @Override
    public void putCoffee() { System.out.println("Dat ca phe len ban Hien dai: Mat kinh cuong luc."); }
    @Override
    public String getSize() { return "80x80cm"; }
}