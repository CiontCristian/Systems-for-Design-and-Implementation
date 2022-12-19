package repo;

import domain.Book;
import domain.validators.Validator;
import domain.validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.*;
import java.util.*;

public class BookDBRepository implements SortingRepository<Long, Book> {
    //private static final String URL="jdbc:postgresql://localhost:5432/postgres";
    //private static final String USER=System.getProperty("username");
    //private static final String PASSWORD=System.getProperty("password");
    //private Connection connection;
    private Validator<Book> validator;
    private Map<Long, Book> entities;
    @Autowired
    private JdbcOperations jdbcOperations;
    public BookDBRepository(Validator<Book> validator)  {
        this.validator=validator;
        this.entities=new HashMap<>();
        /*try {

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }*/
    }

    @Override
    public Iterable<Book> findAll(Sort<Book> sort) {
        Iterable<Book> entities=findAll();
        return sort.sort(entities);
    }

    @Override
    public Optional<Book> findOne(Long id) {
        String sql="select * from Book where id=?";
        List<Book> result= jdbcOperations.query(sql, new Object[] {id} ,(resultSet, rowNum) -> {
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            double price = resultSet.getDouble("price");

            Book book=new Book(title, author, price);
            book.setId(id);

            return  book;
        });
        if(result.size()==0)
            return Optional.empty();

        return Optional.ofNullable(result.get(0));
    }

    @Override
    public Iterable<Book> findAll() {
        String sql="select * from Book";
        return jdbcOperations.query(sql, (resultSet, rowNum) -> {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            double price = resultSet.getDouble("price");

            Book book=new Book(title, author, price);
            book.setId(id);

            return  book;
        });
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidatorException {
        String sql="insert into Book(id, title, author, price) values(?,?,?,?)";
        validator.validate(entity);
        jdbcOperations.update(sql, entity.getId(), entity.getTitle(),entity.getAuthor(),entity.getPrice());

        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<Book> delete(Long id) {
        String sql = "delete from Book where id=?";
        jdbcOperations.update(sql, id);
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException {
        String sql = "update Book set title=?, author=?, price=? where id=?";
        validator.validate(entity);
        jdbcOperations.update(sql, entity.getTitle(),entity.getAuthor(),entity.getPrice(), entity.getId());

        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
