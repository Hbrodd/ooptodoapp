package se.lexicon.dao.DBDao.DBDaoImpl;


import com.sun.tools.javac.comp.Todo;
import se.lexicon.Connection.SQLconnection;
import se.lexicon.dao.DBDao.ToDoItemsDBDao;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ToDoItemsDBDaoImpl implements ToDoItemsDBDao {

    private final PeopleDaoImp peopleDaoImp;

    public ToDoItemsDBDaoImpl(PeopleDaoImp peopleDaoImp) {
        this.peopleDaoImp = peopleDaoImp;
    }

    @Override
    public TodoItem create(TodoItem task) {
        int todoItemId = -1;
        String query = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?,?,?,?,?)";
        try (Connection connection = SQLconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);){
            statement.setString(1,task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(task.getDeadLine()));
            statement.setBoolean(4,task.isDone());
            statement.setInt(5,task.getCreator().getId());
            int rowInserted = statement.executeUpdate();
            if(rowInserted < 0){
                throw new RuntimeException();
            }
            try(ResultSet generatedKey = statement.getGeneratedKeys();) {
                if (generatedKey.next()) {
                    todoItemId = generatedKey.getInt(1);
                }else{throw new RuntimeException();}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        };
        return new TodoItem(todoItemId, task.getTitle(), task.getDescription(),task.getDeadLine(), task.isDone(), task.getCreator());
    }

    @Override
    public Collection<TodoItem> findAll() {
        String query = "SELECT * FROM todo_item";
        List<TodoItem> todoList = new ArrayList<>();
        try (Connection connect = SQLconnection.getConnection();
             Statement statement = connect.createStatement();)
        {
            try(ResultSet items = statement.executeQuery(query);) {
                while (items.next()) {
                    int foundId = items.getInt(1);
                    String title = items.getString(2);
                    String description = items.getString(3);
                    LocalDate deadline = items.getDate(4).toLocalDate();
                    boolean done = items.getBoolean(5);
                    int assigneId = items.getInt(6);
                    if(assigneId > 0) {
                        Person person = peopleDaoImp.findById(assigneId);
                        todoList.add(new TodoItem(foundId, title, description, deadline, done, person));
                        return todoList;
                    }
                    todoList.add(new TodoItem(foundId, title, description, deadline, done));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return todoList;
    }

    @Override
    public TodoItem findById(int id) {
        TodoItem todoItem = null;
        String query = "SELECT * FROM todo_item WHERE todo_id = ?";
        try(Connection connect = SQLconnection.getConnection();
            PreparedStatement statment = connect.prepareStatement(query);)
        {
            statment.setInt(1,id);
            try(ResultSet result = statment.executeQuery();) {
                if (result.next()) {
                    int foundId = result.getInt(1);
                    String title = result.getString(2);
                    String description = result.getString(3);
                    LocalDate deadline = result.getDate(4).toLocalDate();
                    boolean done = result.getBoolean(5);
                    int assigneeId = result.getInt(6);
                    if(assigneeId > 0) {
                        Person person = peopleDaoImp.findById(assigneeId);
                        return new TodoItem(foundId,title,description,deadline,done,person);
                    }
                    todoItem = new TodoItem(foundId,title,description,deadline,done);
                }
            }
        }catch(SQLException e){throw new RuntimeException(e);}

        return todoItem;
    }

    @Override
    public Collection<TodoItem> findByDoneStatus(boolean finished)
    {
        List<TodoItem> todoList = new ArrayList<>();
        String query = "SELECT * FROM todo_item WHERE done = ?";
        try(Connection connect = SQLconnection.getConnection();
            PreparedStatement statment = connect.prepareStatement(query);) {
            statment.setBoolean(1, finished);
            try (ResultSet result = statment.executeQuery();){
            while (result.next()) {
                int foundId = result.getInt(1);
                String title = result.getString(2);
                String description = result.getString(3);
                LocalDate deadline = result.getDate(4).toLocalDate();
                boolean done = result.getBoolean(5);
                int assigneId = result.getInt(6);
                if(assigneId > 0) {
                    Person person = peopleDaoImp.findById(assigneId);
                    todoList.add(new TodoItem(foundId, title, description, deadline, done, person));
                    return todoList;
                }
                todoList.add(new TodoItem(foundId, title, description, deadline, done));
            }
        }
        }catch(SQLException e){throw new RuntimeException(e);}
        return todoList;
    }

    @Override
    public Collection<TodoItem> findByAssignee(int assignee) {
        List<TodoItem> todoList = new ArrayList<>();
        String query = "SELECT * FROM todo_item WHERE assignee_id = ?";
        try(Connection connect = SQLconnection.getConnection();
            PreparedStatement statment = connect.prepareStatement(query);)
        {
            statment.setInt(1,assignee);
            try(ResultSet result = statment.executeQuery();) {
                while (result.next()) {
                    int foundId = result.getInt(1);
                    String title = result.getString(2);
                    String description = result.getString(3);
                    LocalDate deadline = result.getDate(4).toLocalDate();
                    boolean done = result.getBoolean(5);
                    int assigneId = result.getInt(6);
                    if(assigneId > 0) {
                        Person person = peopleDaoImp.findById(assigneId);
                        todoList.add(new TodoItem(foundId, title, description, deadline, done, person));
                        return todoList;
                    }
                    todoList.add(new TodoItem(foundId, title, description, deadline, done));
                }
            }
        }catch(SQLException e){throw new RuntimeException(e);}
        return todoList;
    }

    @Override
    public Collection<TodoItem> findByAssignee(Person assignee)
    {
        List<TodoItem> todoList = new ArrayList<>();
        String query = "SELECT * FROM todo_item WHERE assignee_id = ?";
        try(Connection connect = SQLconnection.getConnection();
            PreparedStatement statment = connect.prepareStatement(query);)
        {
            statment.setInt(1,assignee.getId());
            try(ResultSet result = statment.executeQuery();) {
                while (result.next()) {
                    int foundId = result.getInt(1);
                    String title = result.getString(2);
                    String description = result.getString(3);
                    LocalDate deadline = result.getDate(4).toLocalDate();
                    boolean done = result.getBoolean(5);
                    int assigneId = result.getInt(6);
                    if(assigneId > 0) {
                        Person person = peopleDaoImp.findById(assigneId);
                        todoList.add(new TodoItem(foundId, title, description, deadline, done, person));
                        return todoList;
                    }
                    todoList.add(new TodoItem(foundId, title, description, deadline, done));
                }
            }
        }catch(SQLException e){throw new RuntimeException(e);}
        return todoList;
    }

    @Override
    //Not finished

    public Collection<TodoItem> findByUnassigneedToDoItems() {
        List<TodoItem> todoList = new ArrayList<>();
        String query = "SELECT * FROM todo_item WHERE assignee_id IS NULL";
        try (Connection connect = SQLconnection.getConnection();
             PreparedStatement statement = connect.prepareStatement(query);) {
            try (ResultSet result = statement.executeQuery();) {
                while (result.next()) {
                    int foundId = result.getInt(1);
                    String title = result.getString(2);
                    String description = result.getString(3);
                    LocalDate deadline = result.getDate(4).toLocalDate();
                    boolean done = result.getBoolean(5);
                    todoList.add(new TodoItem(foundId, title, description, deadline,done));
                }

            }
                } catch (SQLException e) {throw new RuntimeException(e);}
            return todoList;
        }

    @Override
    public TodoItem update(TodoItem newUpdatedPerson) {
        String query = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";
        try(Connection connect = SQLconnection.getConnection();
            PreparedStatement statement = connect.prepareStatement(query);)
        {
            statement.setString(1, newUpdatedPerson.getTitle());
            statement.setString(2, newUpdatedPerson.getDescription());
            statement.setDate(3,java.sql.Date.valueOf(newUpdatedPerson.getDeadLine()));
            statement.setBoolean(4, newUpdatedPerson.isDone());
            if (newUpdatedPerson.getCreator() != null) {
                statement.setInt(5, newUpdatedPerson.getCreator().getId());
            } else {
                statement.setNull(5, java.sql.Types.INTEGER);
            }
            statement.setInt(6,newUpdatedPerson.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows == 0){
                throw new RuntimeException("It did not update");
            }

        }catch (SQLException e){throw new RuntimeException(e);}
        return newUpdatedPerson;
    }

    @Override
    public boolean deleteById(int id)
    {   String query = "DELETE FROM todo_item WHERE id = ?";
        try(Connection connection = SQLconnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("The deletion was a success!");
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
