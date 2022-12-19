package lab10.core.service;

import lab10.core.domain.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAll();

    Client save(Client client);

    Client update(Long id, Client client);

    void delete(Long id);

    Client getOne(Long id);
}
