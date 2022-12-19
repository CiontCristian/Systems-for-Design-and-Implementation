package Service;

import domain.BaseEntity;
import domain.validators.ValidatorException;

import java.util.Set;

public interface Service<ID, T extends BaseEntity<ID>> {
    boolean add(T entity) throws ValidatorException;
    boolean delete(ID id) throws IllegalArgumentException;
    boolean update(T entity) throws ValidatorException;
    T getOne(ID id) throws IllegalArgumentException;
    Set<T> getAll();
}
