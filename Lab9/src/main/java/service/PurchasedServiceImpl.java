package service;

import domain.Purchased;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.PurchasedRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PurchasedServiceImpl implements PurchasedService {
    @Autowired
    private PurchasedRepository purchasedRepository;

    @Override
    public List<Purchased> getAll() {
        return purchasedRepository.findAll();
    }

    @Override
    public void save(Purchased purchased) {
        purchasedRepository.save(purchased);
    }

    @Override
    public void update(Purchased purchased) {
        purchasedRepository.findById(purchased.getId()).ifPresent(newPurchased ->{
            newPurchased.setBookID(purchased.getBookID());
            newPurchased.setClientID(purchased.getClientID());
        });
    }

    @Override
    public void delete(Long id) {
        purchasedRepository.deleteById(id);
    }

    @Override
    public Optional<Purchased> getOne(Long id) {
        return purchasedRepository.findById(id);
    }
}
