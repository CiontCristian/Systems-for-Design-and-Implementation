package lab10.core.service;


import lab10.core.domain.Purchased;
import lab10.core.repo.PurchasedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public Purchased save(Purchased purchased) {
        return purchasedRepository.save(purchased);
    }


}
