package repo;

import domain.Book;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.sql.*;
import java.util.*;

public class BookDBRepository implements SortingRepository<Long, Book> {
    private String URL="jdbc:postgresql://localhost:5432/postgres";
    private Connection connection;
    private Validator<Book> validator;
    private Map<Long, Book> entities;

    public BookDBRepository(Validator<Book> validator)  {
        this.validator=validator;
        this.entities=new HashMap<>();
        try {

            connection = DriverManager.getConnection(URL, "postgres", "1234");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Book> findAll(Sort<Book> sort) {
        return null;
    }

    @Override
    public Optional<Book> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<Book> findAll() {
        String sql="select * from Book";
        List<Book> result=new ArrayList<>();

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");

                Book book=new Book(title, author, price);
                book.setId(id);
                result.add(book);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidatorException {
        String sql="insert into Book(id, title, author, price) values(?,?,?,?)";
        validator.validate(entity);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getTitle());
            preparedStatement.setString(3, entity.getAuthor());
            preparedStatement.setDouble(4, entity.getPrice());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<Book> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException {
        return Optional.empty();
    }
}
