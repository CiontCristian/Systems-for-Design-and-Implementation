package Service;

import domain.Client;

import java.util.List;
import java.util.concurrent.Future;

public interface ClientService extends Service<Long, Client> {
    List<Client> filterClientsByAge(int age);
}
