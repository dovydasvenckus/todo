package com.dovydasvenckus.todo.list;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TodoListRepositoryImpl implements TodoListRepository {

    private Sql2o sql2o;
    private Map<String, String> columnMap;

    public TodoListRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
        this.columnMap = getColumnMap();
        this.sql2o.setDefaultColumnMappings(columnMap);
    }

    private HashMap<String, String> getColumnMap() {
        HashMap<String, String> columnMap = new HashMap<>();
        columnMap.put("list_id", "id");
        columnMap.put("created_at", "createdAt");
        columnMap.put("updated_at", "updatedAt");
        return columnMap;
    }

    @Override
    public Optional<TodoList> findById(Long id) {
        try (Connection conn = sql2o.open()) {
            TodoList todoList = conn
                    .createQuery("SELECT * FROM list WHERE list_id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(TodoList.class);

            return Optional.ofNullable(todoList);
        }
    }

    @Override
    public Optional<TodoList> findInbox() {
        return findById(1L);
    }

    @Override
    public List<TodoList> listAll() {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM list")
                    .executeAndFetch(TodoList.class);
        }
    }

    @Override
    public void create(TodoList todoList) {
        try (Connection conn = sql2o.open()) {
            Long id = conn.createQuery("INSERT INTO list(title, created_at, updated_at) " +
                    "VALUES (:title, :created, :updated)", true)
                    .addParameter("title", todoList.getTitle())
                    .addParameter("created", todoList.getCreatedAt())
                    .addParameter("updated", todoList.getUpdatedAt())
                    .executeUpdate()
                    .getKey(Long.class);

            todoList.setId(id);
        }
    }
}
