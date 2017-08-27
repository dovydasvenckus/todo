package com.dovydasvenckus.todo.list;

import com.dovydasvenckus.todo.utils.Service;
import com.dovydasvenckus.jdbc.mapping.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dovydasvenckus.common.StringUtils.isNullOrEmpty;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class TodoListService implements Service {
    private final static Logger logger = LoggerFactory.getLogger(TodoListService.class);

    private final TodoListRepository todoListRepository;

    public TodoListService(Connection dataSource) {
        this.todoListRepository = new TodoListRepositoryImpl(dataSource);
    }

    public Optional<TodoList> findById(long id) {
        try {
            return todoListRepository.findById(id);
        } catch (SQLException | MappingException ex) {
            logger.error("SQL or mapping failure", ex);
            return Optional.empty();
        }
    }

    public Optional<TodoList> getInbox() {
        try {
            return todoListRepository.findInbox();
        } catch (SQLException | MappingException ex) {
            logger.error("SQL or mapping failure", ex);
            return Optional.empty();
        }
    }

    public List<TodoList> getLists() {
        try {
            return todoListRepository.listAll();
        } catch (SQLException | MappingException ex) {
            logger.error("SQL or mapping failure", ex);
            return new ArrayList<>();
        }
    }

    public Optional<TodoList> create(CreateTodoListDto createTodoListDto) {
        if (!isNullOrEmpty(createTodoListDto.getTitle())) {
            TodoList todoList = new TodoList(createTodoListDto.getTitle());
            try {
                todoListRepository.create(todoList);
            } catch (SQLException | MappingException ex) {
                logger.error("SQL or mapping failure", ex);
            }

            return of(todoList);
        }
        return empty();
    }
}
