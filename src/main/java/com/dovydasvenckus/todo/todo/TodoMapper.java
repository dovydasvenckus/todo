package com.dovydasvenckus.todo.todo;

import com.dovydasvenckus.todo.util.sql.mapping.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoMapper extends ResultSetMapper<Todo> {

    @Override
    protected Todo createEntity(ResultSet resultSet) throws SQLException {
        Todo todo = new Todo();
        todo.setId(resultSet.getLong("todo_id"));
        todo.setTitle(resultSet.getString("title"));
        todo.setIsDone(resultSet.getBoolean("is_done"));
        todo.setTodoListId(resultSet.getLong("list_id"));
        todo.setCreatedAt(resultSet.getDate("created_at"));
        todo.setUpdatedAt(resultSet.getDate("updated_at"));
        return todo;
    }
}
