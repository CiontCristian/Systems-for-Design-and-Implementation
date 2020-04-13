package service;

import domain.BaseEntity;
import domain.validators.ValidatorException;
import repo.SortingRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service<ID, T extends BaseEntity<ID>> {
    protected SortingRepository<ID, T> repository;

    public Service(SortingRepository<ID, T> _repo){
        repository=_repo;
    }

    public void add(T entity) throws ValidatorException {
        repository.save(entity);
    }

    public void delete(ID id) throws IllegalArgumentException{
        repository.delete(id);
    }

    public void update(T entity) throws ValidatorException {
        repository.update(entity);
    }

    public void getOne(ID id) throws IllegalArgumentException{
        Optional<T> entity=repository.findOne(id);
        if(!entity.isPresent())
            throw new IllegalArgumentException();

        //return entity.get();
    }

    public Set<T> getAll(){
        Iterable<T> entities=repository.findAll();
        return StreamSupport.stream(entities.spliterator(), false).collect(Collectors.toSet());
    }

}
