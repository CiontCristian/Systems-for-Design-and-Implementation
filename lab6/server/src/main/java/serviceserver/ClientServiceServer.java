package serviceserver;

import Service.ClientService;
import domain.Client;
import repo.SortingRepository;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientServiceServer extends ServiceServer<Long, Client> implements ClientService {
    public ClientServiceServer(SortingRepository<Long, Client> _repo, ExecutorService _executorService) {
        super(_repo, _executorService);
    }

    @Override
    public synchronized Future<Object> filterClientsByAge(int age) {
        Iterable<Client> clients=repository.findAll();
        Set<Client> set= StreamSupport.stream(clients.spliterator(),false)
                .filter(client->client.getAge()==age)
                .collect(Collectors.toSet());

        return executorService.submit(()->set);
    }

}
