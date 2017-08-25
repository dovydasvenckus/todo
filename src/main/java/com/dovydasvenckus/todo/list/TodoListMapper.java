package com.dovydasvenckus.todo.list;

import com.dovydasvenckus.todo.utils.db.sql.mapping.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class TodoListMapper extends ResultSetMapper<TodoList> {

    protected TodoList createEntity(ResultSet resultSet) throws SQLException {
        TodoList todoList = new TodoList();
        todoList.setId(resultSet.getLong("list_id"));
        todoList.setTitle(resultSet.getString("title"));
        todoList.setCreatedAt(resultSet.getDate("created_at"));
        todoList.setUpdatedAt(resultSet.getDate("updated_at"));
        return todoList;
    }
}
