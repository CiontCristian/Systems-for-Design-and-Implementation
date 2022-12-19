package serviceserver;

import Service.ClientService;
import domain.Client;
import repo.SortingRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ClientServiceServer extends ServiceServer<Long, Client> implements ClientService {
    public ClientServiceServer(SortingRepository<Long, Client> _repo) {
        super(_repo);
    }


    @Override
    public List<Client> filterClientsByAge(int age) {
        Iterable<Client> clients=repository.findAll();
        return StreamSupport.stream(clients.spliterator(),false)
                .filter(client->client.getAge()==age)
                .collect(Collectors.toList());
    }
}
