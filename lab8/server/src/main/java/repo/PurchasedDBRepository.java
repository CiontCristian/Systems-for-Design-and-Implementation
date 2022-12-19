package repo;

import domain.Book;
import domain.Purchased;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.*;
import java.util.*;

public class PurchasedDBRepository implements SortingRepository<Long, Purchased> {
    private Validator<Purchased> validator;
    private Map<Long, Purchased> entities;
    @Autowired
    private JdbcOperations jdbcOperations;
    public PurchasedDBRepository(Validator<Purchased> _validator){
        validator=_validator;
        entities=new HashMap<>();

    }

    @Override
    public Iterable<Purchased> findAll(Sort<Purchased> sort) {
        Iterable<Purchased> entities=findAll();
        return sort.sort(entities);
    }

    @Override
    public Optional<Purchased> findOne(Long id) {
        String sql="select * from Purchased where id=?";
        List<Purchased> result= jdbcOperations.query(sql, new Object[] {id}, (resultSet, rowNum) -> {
            Long id2 = resultSet.getLong("id");
            long bookID = resultSet.getLong("bookID");
            long clientID = resultSet.getLong("clientID");

            Purchased purchased = new Purchased(bookID, clientID);
            purchased.setId(id2);

            return  purchased;
        });
        if(result.size()==0)
            return Optional.empty();

        return Optional.ofNullable(result.get(0));
    }

    @Override
    public Iterable<Purchased> findAll() {
        String sql="select * from Purchased";
        return jdbcOperations.query(sql, (resultSet, rowNum) -> {
            Long id2 = resultSet.getLong("id");
            long bookID = resultSet.getLong("bookID");
            long clientID = resultSet.getLong("clientID");

            Purchased purchased = new Purchased(bookID, clientID);
            purchased.setId(id2);

            return  purchased;
        });

    }

    @Override
    public Optional<Purchased> save(Purchased entity) throws ValidatorException {
        String sql="insert into Purchased(id, bookID, clientID) values(?,?,?)";
        validator.validate(entity);
        jdbcOperations.update(sql, entity.getId(), entity.getBookID(),entity.getClientID());

        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<Purchased> delete(Long id) {
        String sql = "delete from Purchased where id=?";
        jdbcOperations.update(sql, id);
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<Purchased> update(Purchased entity) throws ValidatorException {
        String sql = "update Purchased set bookID=?, clientID=? where id=?";
        validator.validate(entity);
        jdbcOperations.update(sql, entity.getId(), entity.getBookID(),entity.getClientID());

        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
