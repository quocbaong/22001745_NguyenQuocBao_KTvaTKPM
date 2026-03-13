import logistics.Logistics;
import logistics.RoadLogistics;
import logistics.SeaLogistics;

public class Main {
    public static void main(String[] args) {
        // 1. Giả sử hôm nay cần giao hàng đường bộ
        System.out.println("--- Cau hinh Logistics Duong Bo ---");
        Logistics roadLog = new RoadLogistics();
        roadLog.planDelivery();

        System.out.println();

        // 2. Giả sử hôm nay cần giao hàng đường thủy
        System.out.println("--- Cau hinh Logistics Duong Thuy ---");
        Logistics seaLog = new SeaLogistics();
        seaLog.planDelivery();
    }
}