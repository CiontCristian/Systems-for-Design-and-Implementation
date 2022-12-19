package serviceserver;

import Service.Service;
import domain.BaseEntity;
import domain.Book;
import domain.validators.ValidatorException;
import repo.SortingRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceServer<ID, T extends BaseEntity<ID>> implements Service<ID, T> {
    protected SortingRepository<ID, T> repository;
    protected ExecutorService executorService;

    public ServiceServer(SortingRepository<ID, T> _repo, ExecutorService _executorService){
        repository=_repo;
        executorService=_executorService;
    }

    public synchronized Future<Object> add(T entity) throws ValidatorException {
        repository.save(entity);
        return executorService.submit(() -> "success!");
    }

    public synchronized Future<Object> delete(ID id) throws IllegalArgumentException{
        repository.delete(id);
        return executorService.submit(() -> "success!");
    }

    public synchronized Future<Object> update(T entity) throws ValidatorException {
        repository.update(entity);
        return executorService.submit(() -> "success!");
    }

    public synchronized Future<Object> getOne(ID id) throws IllegalArgumentException{
        Optional<T> entity=repository.findOne(id);
        if(!entity.isPresent())
            throw new IllegalArgumentException();

        return executorService.submit(() -> "success!");
    }

    public synchronized Future<Object> getAll(){
       Iterable<T> books=repository.findAll();
       Set<T> set=StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());

       return executorService.submit(() -> set);
    }

}
