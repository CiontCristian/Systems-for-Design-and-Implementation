package repo;

import domain.Purchased;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.sql.*;
import java.util.*;

public class PurchasedDBRepository implements SortingRepository<Long, Purchased> {
    private static final String URL="jdbc:postgresql://localhost:5432/postgres";
    private static final String USER=System.getProperty("username");
    private static final String PASSWORD=System.getProperty("password");
    private Connection connection;
    private Validator<Purchased> validator;
    private Map<Long, Purchased> entities;

    public PurchasedDBRepository(Validator<Purchased> _validator){
        validator=_validator;
        entities=new HashMap<>();
        try {

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Iterable<Purchased> findAll(Sort<Purchased> sort) {
        Iterable<Purchased> entities=findAll();
        return sort.sort(entities);
    }

    @Override
    public Optional<Purchased> findOne(Long id) {
        String sql="select * from Purchased where id=?";
        List<Purchased> result=new ArrayList<>();

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id2 = resultSet.getLong("id");
                long bookID = resultSet.getLong("bookID");
                long clientID = resultSet.getLong("clientID");

                Purchased purchased = new Purchased(bookID, clientID);
                purchased.setId(id2);
                result.add(purchased);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(result.get(0));
    }

    @Override
    public Iterable<Purchased> findAll() {
        String sql="select * from Purchased";
        List<Purchased> result=new ArrayList<>();

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id2 = resultSet.getLong("id");
                long bookID = resultSet.getLong("bookID");
                long clientID = resultSet.getLong("clientID");

                Purchased purchased = new Purchased(bookID, clientID);
                purchased.setId(id2);
                result.add(purchased);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<Purchased> save(Purchased entity) throws ValidatorException {
        String sql="insert into Purchased(id, bookID, clientID) values(?,?,?)";
        validator.validate(entity);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setLong(2, entity.getBookID());
            preparedStatement.setLong(3, entity.getClientID());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<Purchased> delete(Long id) {
        String sql = "delete from Purchased where id=?";

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
    public Optional<Purchased> update(Purchased entity) throws ValidatorException {
        String sql = "update Purchased set bookID=?, clientID=? where id=?";
        validator.validate(entity);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getBookID());
            preparedStatement.setLong(2, entity.getClientID());
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
