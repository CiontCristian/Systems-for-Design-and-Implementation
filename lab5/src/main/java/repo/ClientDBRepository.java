package repo;

import domain.Book;
import domain.Client;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.sql.*;
import java.util.*;

public class ClientDBRepository implements SortingRepository<Long, Client> {
    private static final String URL="jdbc:postgresql://localhost:5432/postgres";
    private static final String USER=System.getProperty("username");
    private static final String PASSWORD=System.getProperty("password");
    private Connection connection;
    private Validator<Client> validator;
    private Map<Long, Client> entities;

    public ClientDBRepository(Validator<Client> _validator){
        validator=_validator;
        entities=new HashMap<>();
        try {

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public Iterable<Client> findAll(Sort<Client> sort) {
        Iterable<Client> entities=findAll();
        return sort.sort(entities);
    }

    @Override
    public Optional<Client> findOne(Long id) {
        String sql="select * from Client where id=?";
        List<Client> result=new ArrayList<>();

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id2 = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");

                Client client = new Client(firstName, lastName, age);
                client.setId(id2);
                result.add(client);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(result.get(0));
    }

    @Override
    public Iterable<Client> findAll() {
        String sql="select * from Client";
        List<Client> result=new ArrayList<>();

        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id2 = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");

                Client client = new Client(firstName, lastName, age);
                client.setId(id2);
                result.add(client);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<Client> save(Client entity) throws ValidatorException {
        String sql="insert into Client(id, firstName, lastName, age) values(?,?,?,?)";
        validator.validate(entity);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setDouble(4, entity.getAge());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<Client> delete(Long id) {
        String sql = "delete from Client where id=?";

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
    public Optional<Client> update(Client entity) throws ValidatorException {
        String sql = "update Client set firstName=?, lastName=?, age=? where id=?";
        validator.validate(entity);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setDouble(3, entity.getAge());
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
