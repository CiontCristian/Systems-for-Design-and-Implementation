package repo;

import domain.Book;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.*;
import java.util.*;
import java.util.stream.StreamSupport;

public class BookDBRepository implements SortingRepository<Long, Book> {
    private static final String URL="jdbc:postgresql://localhost:5432/postgres";
    private static final String USER=System.getProperty("username");
    private static final String PASSWORD=System.getProperty("password");
    private Connection connection;
    private Validator<Book> validator;
    private Map<Long, Book> entities;

    public BookDBRepository(Validator<Book> validator)  {
        this.validator=validator;
        this.entities=new HashMap<>();
        try {

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Book> findAll(Sort<Book> sort) {
        Iterable<Book> entities=findAll();
        return sort.sort(entities);
    }

    @Override
    public Optional<Book> findOne(Long id) {
        String sql="select * from Book where id=?";
        List<Book> result=new ArrayList<>();

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next()) {
                Long id2 = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                double price = resultSet.getDouble("price");

                Book book = new Book(title, author, price);
                book.setId(id2);
                result.add(book);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(result.get(0));
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
    public Optional<Book> delete(Long id) {
        String sql = "delete from Book where id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException {
        String sql = "update Book set title=?, author=?, price=? where id=?";
        validator.validate(entity);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getAuthor());
            preparedStatement.setDouble(3, entity.getPrice());
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
