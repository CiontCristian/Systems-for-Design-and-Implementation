package Service;

import domain.BaseEntity;
import domain.validators.ValidatorException;

import java.util.Set;
import java.util.concurrent.Future;

public interface Service<ID, T extends BaseEntity<ID>> {
    String add="add";
    String delete="delete";
    String getAll="getAll";
    String success="success";
    String error="error";
    String update="update";
    String getOne="getOne";

    public Future<Object> add(T entity) throws ValidatorException;
    public Future<Object> delete(ID id) throws IllegalArgumentException;
    public Future<Object> update(T entity) throws ValidatorException;
    public Future<Object> getOne(ID id) throws IllegalArgumentException;
    public Future<Object> getAll();
}
