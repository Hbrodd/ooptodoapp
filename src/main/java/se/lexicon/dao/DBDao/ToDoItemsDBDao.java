package se.lexicon.dao.DBDao;

import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.util.Collection;

public interface ToDoItemsDBDao {
    TodoItem create(TodoItem task);
    Collection<TodoItem> findAll();
    TodoItem findById(int id);
    Collection<TodoItem> findByDoneStatus(boolean finished);
    Collection<TodoItem> findByAssignee(int assignee);
    Collection<TodoItem> findByAssignee(Person assignee);
    Collection<TodoItem> findByUnassigneedToDoItems();
    TodoItem update(TodoItem newUpdatedPerson);
    boolean deleteById (int id);
}
