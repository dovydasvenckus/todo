package com.dovydasvenckus.todo.todo;

import com.dovydasvenckus.todo.list.TodoList;
import com.dovydasvenckus.jdbc.common.JDBCUtils;
import com.dovydasvenckus.jdbc.mapping.MappingException;
import com.dovydasvenckus.jdbc.mapping.ResultSetToListMapper;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TodoRepositoryImpl implements TodoRepository {

    private Connection connection;

    private TodoMapper mapper = new TodoMapper();

    public TodoRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Todo> find(Long id) throws SQLException, MappingException {

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo WHERE todo_id = ?")) {
            statement.setLong(1, id);
            return Optional.ofNullable(mapper.map(statement.executeQuery()));
        }
    }

    @Override
    public List<Todo> listAll() throws SQLException, MappingException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo")) {
            ResultSet resultSet = statement.executeQuery();
            return new ResultSetToListMapper<>(mapper).getListOfResults(resultSet);
        }
    }

    @Override
    public List<Todo> listDone() throws SQLException, MappingException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo WHERE is_done = TRUE")) {
            ResultSet resultSet = statement.executeQuery();
            return new ResultSetToListMapper<>(mapper).getListOfResults(resultSet);
        }
    }

    @Override
    public List<Todo> listActive() throws SQLException, MappingException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo WHERE list_id = ? AND is_done = FALSE")) {
            statement.setLong(1, 1);

            ResultSet resultSet = statement.executeQuery();
            return new ResultSetToListMapper<>(mapper).getListOfResults(resultSet);
        }
    }

    @Override
    public List<Todo> list(TodoList todoList) throws SQLException, MappingException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM todo WHERE list_id = ?")) {
            statement.setLong(1, todoList.getId());

            ResultSet resultSet = statement.executeQuery();
            return new ResultSetToListMapper<>(mapper).getListOfResults(resultSet);
        }
    }


    @Override
    public void add(Todo todo) throws SQLException, MappingException {
        String addStatement = "INSERT INTO todo(title, list_id, is_done, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(addStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, todo.getTitle());
            statement.setLong(2, todo.getTodoListId());
            statement.setBoolean(3, todo.getIsDone());
            statement.setDate(4, new java.sql.Date(todo.getCreatedAt().getTime()));
            statement.setDate(5, new java.sql.Date(todo.getUpdatedAt().getTime()));

            statement.executeUpdate();
            todo.setId(JDBCUtils.extractGeneratedId(statement.getGeneratedKeys()).get());
        }
    }

    @Override
    public void update(Todo todo) throws SQLException, MappingException {
        String updateQuery = "UPDATE todo " +
                "SET title = ?, is_done = ?, updated_at = ?, list_id = ?" +
                "WHERE todo_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, todo.getTitle());
            statement.setBoolean(2, todo.getIsDone());
            statement.setDate(3, new java.sql.Date(new Date().getTime()));
            statement.setLong(4, todo.getTodoListId());
            statement.setLong(5, todo.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void remove(Long id) throws SQLException, MappingException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM todo WHERE todo_id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
