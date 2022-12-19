package serviceclient;

import Service.ClientService;
import domain.Client;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class ClientServiceClient extends ServiceClient<Long, Client> implements ClientService {
    public ClientServiceClient() {
        super();
    }
    @Autowired
    private ClientService clientService;

    @Override
    public List<Client> filterClientsByAge(int age) {
        return clientService.filterClientsByAge(age);
    }
}
