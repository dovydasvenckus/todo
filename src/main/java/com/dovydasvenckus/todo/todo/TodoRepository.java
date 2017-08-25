package com.dovydasvenckus.todo.todo;

import com.dovydasvenckus.todo.list.TodoList;
import com.dovydasvenckus.todo.utils.db.sql.mapping.MappingException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Optional<Todo> find(Long id) throws SQLException, MappingException;

    List<Todo> listAll() throws SQLException, MappingException;

    List<Todo> listDone() throws SQLException, MappingException;

    List<Todo> listActive() throws SQLException, MappingException;

    List<Todo> list(TodoList todoList) throws SQLException, MappingException;

    void add(Todo todo) throws SQLException, MappingException;

    void update(Todo todo) throws SQLException, MappingException;

    void remove(Long id) throws SQLException, MappingException;

}
