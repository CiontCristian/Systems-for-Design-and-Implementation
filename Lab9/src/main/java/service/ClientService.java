package service;

import domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAll();

    void save(Client client);

    void update(Client client);

    void delete(Long id);

    Optional<Client> getOne(Long id);
}
