package com.dovydasvenckus.todo.todo;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.*;

public class TodoRepositoryImpl implements TodoRepository {

    private Sql2o sql2o;
    private Map<String, String > columnMap;
    public TodoRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
        initColMap();
    }

    private void initColMap() {
        columnMap = new HashMap<>();
        columnMap.put("todo_id", "id");
        columnMap.put("is_done", "isDone");
        columnMap.put("created_at", "createdAt");
        columnMap.put("updated_at", "updatedAt");

        this.sql2o.setDefaultColumnMappings(columnMap);
    }

    @Override
    public Optional<Todo> find(Long id) {
        try (Connection conn = sql2o.open()) {
            Todo todo = conn
                    .createQuery("SELECT * FROM todo WHERE todo_id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Todo.class);
            return Optional.ofNullable(todo);
        }
    }

    @Override
    public List<Todo> listAll() {
        try (Connection conn = sql2o.open()) {
            return conn
                    .createQuery("SELECT * FROM todo")
                    .executeAndFetch(Todo.class);
        }
    }

    @Override
    public List<Todo> listDone() {
        try (Connection conn = sql2o.open()) {
            return conn
                    .createQuery("SELECT * FROM todo WHERE is_done = TRUE")
                    .executeAndFetch(Todo.class);
        }
    }

    @Override
    public List<Todo> listActive() {
        try (Connection conn = sql2o.open()) {
            return conn
                    .createQuery("SELECT * FROM todo WHERE is_done = FALSE")
                    .executeAndFetch(Todo.class);
        }
    }

    @Override
    public void add(Todo todo) {
        try (Connection conn = sql2o.open()) {
            Long id = (Long) conn.createQuery("INSERT INTO todo(title, is_done, created_at, updated_at) " +
                    "VALUES (:title, :done, :created, :updated)", true)
                    .addParameter("title", todo.getTitle())
                    .addParameter("done", todo.getIsDone())
                    .addParameter("created", todo.getCreatedAt())
                    .addParameter("updated", todo.getUpdatedAt())
                    .executeUpdate()
                    .getKey();

            todo.setId(id);
        }
    }

    @Override
    public void update(Todo todo) {
        try (Connection conn = sql2o.open()) {
            conn
                    .createQuery("UPDATE todo " +
                            "SET title = :title, is_done = :done, updated_at = :updated " +
                            "WHERE todo_id = :id")
                    .addParameter("id", todo.getId())
                    .addParameter("title", todo.getTitle())
                    .addParameter("done", todo.getIsDone())
                    .addParameter("updated", new Date())
                    .executeUpdate();
        }
    }

    @Override
    public void remove(Long id) {
        try (Connection conn = sql2o.open()) {
            conn
                    .createQuery("DELETE FROM todo WHERE todo_id = :id")
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
