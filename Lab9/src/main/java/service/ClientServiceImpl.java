package service;

import domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void update(Client client) {
        clientRepository.findById(client.getId()).ifPresent(newClient ->{
            newClient.setFirstName(client.getFirstName());
            newClient.setLastName(client.getLastName());
            newClient.setAge(client.getAge());
        });
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Optional<Client> getOne(Long id) {
        return clientRepository.findById(id);
    }
}
