package client;

import factories.FurnitureFactory;
import products.Chair;
import products.CoffeeTable;
import products.Sofa;

public class InteriorDesigner {
    private Chair chair;
    private Sofa sofa;
    private CoffeeTable table;

    // Client chỉ làm việc với Interface, không quan tâm class cụ thể là gì
    public InteriorDesigner(FurnitureFactory factory) {
        this.chair = factory.createChair();
        this.sofa = factory.createSofa();
        this.table = factory.createCoffeeTable();
    }

    public void decorate() {
        System.out.println("--- Bat dau setup noi that cho can phong ---");
        chair.sitOn();
        sofa.lieOn();
        table.putCoffee();
        System.out.println("--- Hoan tat trang tri ---\n");
    }
}
