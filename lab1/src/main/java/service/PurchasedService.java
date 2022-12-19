package service;

import domain.Purchased;
import repo.Repository;
import repo.SortingRepository;

public class PurchasedService extends Service<Long, Purchased> {

    public PurchasedService(SortingRepository<Long, Purchased> repository){
        super(repository);
    }
}
