package service;

import domain.Client;
import repo.SortingRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ClientService extends Service<Long, Client> {

    public ClientService(SortingRepository<Long, Client> repository){
        super(repository);
    }

    public Set<Client> filterClientsByAge(int age){
        Iterable<Client> clients=super.getAll();

        return StreamSupport.stream(clients.spliterator(), false)
                .filter(client -> client.getAge() < age).collect(Collectors.toSet());
    }

}
