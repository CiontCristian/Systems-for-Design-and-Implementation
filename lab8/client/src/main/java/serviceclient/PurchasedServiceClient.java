package serviceclient;

import Service.PurchasedService;
import domain.Purchased;

public class PurchasedServiceClient extends ServiceClient<Long, Purchased> implements PurchasedService {
    public PurchasedServiceClient() {
        super();
    }


}
