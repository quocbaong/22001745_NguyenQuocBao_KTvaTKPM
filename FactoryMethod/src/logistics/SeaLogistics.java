package logistics;

import transport.Ship;
import transport.Transport;

public class SeaLogistics extends Logistics{
    @Override
    public Transport createTransport() {
        return new Ship();
    }
}
