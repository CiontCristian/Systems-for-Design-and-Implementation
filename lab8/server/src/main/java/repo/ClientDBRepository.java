package repo;

import domain.Book;
import domain.Client;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.*;
import java.util.*;

public class ClientDBRepository implements SortingRepository<Long, Client> {
    private Validator<Client> validator;
    private Map<Long, Client> entities;
    @Autowired
    private JdbcOperations jdbcOperations;

    public ClientDBRepository(Validator<Client> _validator){
        validator=_validator;
        entities=new HashMap<>();
    }


    @Override
    public Iterable<Client> findAll(Sort<Client> sort) {
        Iterable<Client> entities=findAll();
        return sort.sort(entities);
    }

    @Override
    public Optional<Client> findOne(Long id) {
        String sql="select * from Client where id=?";
        List<Client> result= jdbcOperations.query(sql, new Object[] {id}, (resultSet, rowNum) -> {
            Long id2 = resultSet.getLong("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            int age = resultSet.getInt("age");

            Client client = new Client(firstName, lastName, age);
            client.setId(id2);

            return  client;
        });
        if(result.size()==0)
            return Optional.empty();

        return Optional.ofNullable(result.get(0));
    }

    @Override
    public Iterable<Client> findAll() {
        String sql="select * from Client";

        return jdbcOperations.query(sql, (resultSet, rowNum) -> {
            Long id2 = resultSet.getLong("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            int age = resultSet.getInt("age");

            Client client = new Client(firstName, lastName, age);
            client.setId(id2);

            return  client;
        });
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        String sql="insert into Client(id, firstName, lastName, age) values(?,?,?,?)";
        validator.validate(entity);

        jdbcOperations.update(sql, entity.getId(), entity.getFirstName(),entity.getLastName(),entity.getAge());

        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<Client> delete(Long id) {
        String sql = "delete from Client where id=?";

        jdbcOperations.update(sql, id);

        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<Client> update(Client entity) throws ValidatorException {
        String sql = "update Client set firstName=?, lastName=?, age=? where id=?";
        validator.validate(entity);

        jdbcOperations.update(sql, entity.getFirstName(),entity.getLastName(),entity.getAge(), entity.getId());

        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
