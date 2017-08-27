package com.dovydasvenckus.todo.list;

import com.dovydasvenckus.jdbc.common.JDBCUtils;
import com.dovydasvenckus.jdbc.mapping.MappingException;
import com.dovydasvenckus.jdbc.mapping.ResultSetToListMapper;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TodoListRepositoryImpl implements TodoListRepository {

    private Connection connection;

    private TodoListMapper mapper = new TodoListMapper();

    public TodoListRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<TodoList> findById(Long id) throws SQLException, MappingException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM list WHERE list_id = ?")) {
            statement.setLong(1, id);
            statement.setMaxRows(1);

            ResultSet result = statement.executeQuery();

            return Optional.ofNullable(mapper.map(result));
        }
    }

    @Override
    public Optional<TodoList> findInbox() throws SQLException, MappingException {
        return findById(1L);
    }

    @Override
    public List<TodoList> listAll() throws SQLException, MappingException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM list")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return new ResultSetToListMapper<>(mapper).getListOfResults(resultSet);
        }
    }

    @Override
    public void create(TodoList todoList) throws SQLException, MappingException {
        String insertStatement = "INSERT INTO list(title, created_at, updated_at) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, todoList.getTitle());
            statement.setDate(2, new Date(todoList.getCreatedAt().getTime()));
            statement.setDate(3, new Date(todoList.getUpdatedAt().getTime()));
            statement.executeUpdate();

            todoList.setId(JDBCUtils.extractGeneratedId(statement.getGeneratedKeys()).get());
        }
    }
}
