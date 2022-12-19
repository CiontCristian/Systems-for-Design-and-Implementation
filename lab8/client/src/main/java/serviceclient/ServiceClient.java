package serviceclient;

import Service.Service;
import domain.BaseEntity;
import domain.validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


public class ServiceClient<ID, T extends BaseEntity<ID>> implements Service<ID, T> {

    public ServiceClient() {}
    @Autowired
    private Service<ID, T> service;

    @Override
    public boolean add(T entity) throws ValidatorException {
        return service.add(entity);
    }

    @Override
    public boolean delete(ID id) throws IllegalArgumentException {
        return service.delete(id);
    }

    @Override
    public boolean update(T entity) throws ValidatorException {
        return service.update(entity);
    }

    @Override
    public T getOne(ID id) throws IllegalArgumentException {
        return service.getOne(id);
    }

    @Override
    public Set<T> getAll() {
        return service.getAll();
    }
}
