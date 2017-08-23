package com.dovydasvenckus.todo.todo;

import com.dovydasvenckus.todo.list.TodoList;
import com.dovydasvenckus.todo.list.TodoListService;
import com.dovydasvenckus.todo.util.Service;
import com.dovydasvenckus.todo.util.sql.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

public class TodoService implements Service {
    private final static Logger logger = LoggerFactory.getLogger(TodoService.class);

    private final TodoListService todoListService;

    private final TodoRepository todoRepository;

    public TodoService(Connection connection, TodoListService todoListService) {
        this.todoRepository = new TodoRepositoryImpl(connection);
        this.todoListService = todoListService;
    }

    public List<Todo> getTodos(TodoStateFilter filter) {
        try {

            switch (filter) {
                case NOT_DONE:
                    return todoRepository.listActive();
                case DONE:
                    return todoRepository.listDone();
                case ALL:
                    return todoRepository.listAll();
                default:
                    throw new IllegalArgumentException();
            }
        } catch (SQLException | MappingException ex) {
            logger.error("SQL or mapping error", ex);
            return new ArrayList<>();
        }
    }

    public List<Todo> getFromList(TodoList todoList) {
        try {
            return todoRepository.list(todoList);
        } catch (SQLException | MappingException ex) {
            logger.error("SQL or mapping error", ex);
            return new ArrayList<>();
        }
    }

    public Optional<Todo> find(Long id) {
        try {
            return todoRepository.find(id);
        } catch (SQLException | MappingException ex) {
            logger.error("SQL or mapping error", ex);
            return Optional.empty();
        }
    }

    public Optional<Todo> create(CreateTodoDto createTodoDto) {
        if (createTodoDto.getTitle() != null) {

            Todo todo = new Todo(createTodoDto.getTitle(), getTodoListId(createTodoDto));
            try {
                todoRepository.add(todo);
            } catch (SQLException | MappingException ex) {
                logger.error("SQL or mapping error", ex);
                return Optional.empty();
            }

            return Optional.of(todo);
        }
        return empty();
    }

    public void delete(Long id) {
        try {
            todoRepository.remove(id);
        } catch (SQLException | MappingException ex) {
            logger.error("SQL or mapping error", ex);
        }
    }

    public void toggleDo(Long id) {

        try {
            Optional<Todo> todo = todoRepository.find(id);
            if (todo.isPresent()) {
                todo.get().toggleDone();
                todoRepository.update(todo.get());
            }
        } catch (SQLException | MappingException ex) {
            logger.error("SQL or mapping error", ex);
        }
    }

    private Long getTodoListId(CreateTodoDto createTodoDto) {
        return createTodoDto.getTodoListId() == null ?
                todoListService.getInbox().map(TodoList::getId).orElse(null) : createTodoDto.getTodoListId();
    }
}
