package products.victorian;
import products.CoffeeTable;

public class VictorianCoffeeTable implements CoffeeTable {
    @Override
    public void putCoffee() { System.out.println("Dat cafe len ban Co dien: Go sooif  cham khac tinh te."); }
    @Override
    public String getSize() { return "120x120cm"; }
}