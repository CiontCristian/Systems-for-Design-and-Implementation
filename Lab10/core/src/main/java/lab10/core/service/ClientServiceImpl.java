package lab10.core.service;


import lab10.core.domain.Client;
import lab10.core.repo.ClientRepository;
import lab10.core.repo.PurchasedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PurchasedRepository purchasedRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client save(Client client) {
       return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        Client updated = clientRepository.findById(id).orElse(client);

        updated.setFirstName(client.getFirstName());
        updated.setLastName(client.getLastName());
        updated.setAge(client.getAge());
        clientRepository.saveAndFlush(updated);

        return updated;
    }

    @Override
    public void delete(Long id) {
        purchasedRepository.findAll().forEach(purchased -> {
            if(purchased.getClientID()==id){
                purchasedRepository.deleteById(purchased.getId());
            }
        });

        clientRepository.deleteById(id);
    }

    @Override
    public Client getOne(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
}
