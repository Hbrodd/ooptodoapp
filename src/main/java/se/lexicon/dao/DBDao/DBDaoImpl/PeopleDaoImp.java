package se.lexicon.dao.DBDao.DBDaoImpl;

import se.lexicon.Connection.SQLconnection;
import se.lexicon.dao.DBDao.PeopleDao;
import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PeopleDaoImp implements PeopleDao {


    @Override
    public Person create(Person person) {
        int personid = -1;
        String insertQuery = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        try (Connection connect = SQLconnection.getConnection();
             PreparedStatement statement = connect.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            int rowsInserted = statement.executeUpdate();
            try(ResultSet generatedKey = statement.getGeneratedKeys()){
                if (!generatedKey.next()){
                    throw new RuntimeException();
                }
                personid = generatedKey.getInt(1);
            }
            if (rowsInserted == 0) {
                throw new RuntimeException();
            }
        } catch (SQLException e) {

        }
        return new Person(personid, person.getFirstName(), person.getLastName());
    }

    @Override
    public Collection<Person> findAll() {
        String query = "select * from person";
        List<Person> personList = new ArrayList<>();
        try (Connection connect = SQLconnection.getConnection();
             Statement statement = connect.createStatement();) {
            try(ResultSet users = statement.executeQuery(query);) {
                while (users.next()) {
                    int foundId = users.getInt(1);
                    String firstName = users.getString(2);
                    String lastName = users.getString(3);
                    personList.add(new Person(foundId, firstName, lastName));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personList;
    }

    @Override
    public Person findById(int id) {
        String firstName = null;
        String lastName = null;
        String query = "SELECT * FROM person WHERE person_id = ?";
        try (Connection connection = SQLconnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try(ResultSet gotUser = statement.executeQuery();) {
                if (gotUser.next()) {
                    firstName = gotUser.getString(2);
                    lastName = gotUser.getString(3);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Person(id, firstName, lastName);
    }

    @Override
    public Collection<Person> findByName(String name) {
        List<Person> personList = new ArrayList<>();
        String query = "SELECT * FROM person WHERE first_name = ?";
        try (Connection connect = SQLconnection.getConnection();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, name);
            try(ResultSet people = statement.executeQuery();) {
                while (people.next()) {
                    int foundId = people.getInt(1);
                    String firstName = people.getString(2);
                    String lastName = people.getString(3);
                    personList.add(new Person(foundId, firstName, lastName));
                }
            }
        } catch (SQLException e) {

        }
        return personList;
    }

    @Override
    public Person update(Person person) {
        String query = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";
        try(Connection connect = SQLconnection.getConnection();
            PreparedStatement statement = connect.prepareStatement(query);)
        {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3,person.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 0){
                throw new RuntimeException();
            }

        }catch (SQLException e){}
        return person;
    }

    @Override
    public Boolean deleteById(int id)
    {
            String query = "DELETE FROM person WHERE person_id = ?";
            try (Connection connection = SQLconnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("The deletion was a sucess!");
                    return true;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
}
