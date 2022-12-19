package Service;

import domain.Client;

import java.util.Set;
import java.util.concurrent.Future;

public interface ClientService extends Service<Long, Client> {
    String filterClientsByAge="filterClientsByAge";
    public Future<Object> filterClientsByAge(int age);
}
