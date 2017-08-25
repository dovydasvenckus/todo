package com.dovydasvenckus.todo.list;

import com.dovydasvenckus.todo.utils.sql.mapping.MappingException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TodoListRepository {

    Optional<TodoList> findInbox() throws SQLException, MappingException;

    Optional<TodoList> findById(Long id) throws SQLException, MappingException;

    List<TodoList> listAll() throws SQLException, MappingException;

    void create(TodoList todoList) throws SQLException, MappingException;

}
