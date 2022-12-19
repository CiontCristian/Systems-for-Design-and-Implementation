package service;

import domain.Client;
import repo.Repository;
import repo.SortingRepository;


public class ClientService extends Service<Long, Client> {

    public ClientService(SortingRepository<Long, Client> repository){
        super(repository);
    }


}
