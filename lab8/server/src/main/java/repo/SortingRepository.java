package repo;

import domain.BaseEntity;

public interface SortingRepository<ID, T extends BaseEntity<ID>> extends Repository<ID, T> {

    Iterable<T> findAll(Sort<T> sort);
}
