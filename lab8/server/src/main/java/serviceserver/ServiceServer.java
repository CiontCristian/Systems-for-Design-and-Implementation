package serviceserver;

import Service.Service;
import domain.BaseEntity;
import domain.validators.ValidatorException;
import repo.SortingRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ServiceServer<ID, T extends BaseEntity<ID>> implements Service<ID, T> {
    protected SortingRepository<ID, T> repository;

    public ServiceServer(SortingRepository<ID, T> _repo){
        repository=_repo;
    }


    @Override
    public boolean add(T entity) throws ValidatorException {
        repository.save(entity);
        return true;
    }

    @Override
    public boolean delete(ID id) throws IllegalArgumentException {
        repository.delete(id);
        return true;
    }

    @Override
    public boolean update(T entity) throws ValidatorException {
        repository.update(entity);
        return true;
    }

    @Override
    public T getOne(ID id) throws IllegalArgumentException {
        repository.findOne(id);
        return null;
    }

    @Override
    public Set<T> getAll() {
        Iterable<T> entities=repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }
}
