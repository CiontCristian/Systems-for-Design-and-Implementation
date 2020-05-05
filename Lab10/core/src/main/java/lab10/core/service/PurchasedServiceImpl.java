package lab10.core.service;

import lab10.core.domain.Book;
import lab10.core.domain.Client;
import lab10.core.domain.Purchased;
import lab10.core.repo.BookRepository;
import lab10.core.repo.ClientRepository;
import lab10.core.repo.PurchasedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PurchasedServiceImpl implements PurchasedService {
    @Autowired
    private PurchasedRepository purchasedRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Purchased> getAll() {
        return purchasedRepository.findAll();
    }

    @Override
    public Purchased save(Purchased purchased) {
        Optional<Book> foundBook = bookRepository.findById(purchased.getBookID());
        Optional<Client> foundClient = clientRepository.findById(purchased.getClientID());
        if(foundBook.isEmpty() || foundClient.isEmpty()){
            return null;
        }

        return purchasedRepository.save(purchased);
    }


}
