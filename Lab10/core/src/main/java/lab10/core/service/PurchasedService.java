package lab10.core.service;



import lab10.core.domain.Purchased;

import java.util.List;
import java.util.Optional;

public interface PurchasedService {
    List<Purchased> getAll();

    Purchased save(Purchased purchased);

}
