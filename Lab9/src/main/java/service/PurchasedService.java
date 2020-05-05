package service;

import domain.Purchased;

import java.util.List;
import java.util.Optional;

public interface PurchasedService {
    List<Purchased> getAll();

    void save(Purchased purchased);

    void update(Purchased purchased);

    void delete(Long id);

    Optional<Purchased> getOne(Long id);
}
