package com.dovydasvenckus.todo.todo;

import com.dovydasvenckus.todo.list.TodoList;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.*;

public class TodoRepositoryImpl implements TodoRepository {

    private Sql2o sql2o;
    private Map<String, String> columnMap;

    public TodoRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
        initColMap();
    }

    private void initColMap() {
        columnMap = new HashMap<>();
        columnMap.put("todo_id", "id");
        columnMap.put("is_done", "isDone");
        columnMap.put("list_id", "todoListId");
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
    public List<Todo> list(TodoList todoList) {
        try (Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT * FROM todo WHERE list_id = :list_id")
                    .addParameter("list_id", todoList.getId())
                    .executeAndFetch(Todo.class);
        }
    }

    @Override
    public List<Todo> listActive() {
        try (Connection conn = sql2o.open()) {
            return conn
                    .createQuery("SELECT * FROM todo WHERE list_id = :list_id AND is_done = FALSE")
                    .addParameter("list_id", 1)
                    .executeAndFetch(Todo.class);
        }
    }

    @Override
    public Long count(Optional<Boolean> isDone) {
        String sql = "SELECT COUNT (todo_id) FROM todo ";
        try (Connection con = sql2o.open()) {
            if (isDone.isPresent()) {
                return con.createQuery(sql + " WHERE is_done = :done")
                        .addParameter("done", isDone.get() ? "TRUE" : "FALSE")
                        .executeScalar(Long.class);
            } else {
                return con.createQuery(sql)
                        .executeScalar(Long.class);
            }
        }

    }

    @Override
    public void batchUpdate(List<Todo> todoList) {
        try (Connection conn = sql2o.beginTransaction()) {
            Query updateQuery = getUpdateQuery(conn);
            todoList.forEach(todo ->
                    addParamsToUpdate(updateQuery, todo).addToBatch());

            updateQuery.executeBatch();
            conn.commit();
        }
    }

    @Override
    public void add(Todo todo) {
        try (Connection conn = sql2o.open()) {
            Long id = conn.createQuery("INSERT INTO todo(title, list_id, is_done, created_at, updated_at) " +
                    "VALUES (:title, :list_id, :done, :created, :updated)", true)
                    .addParameter("title", todo.getTitle())
                    .addParameter("list_id", todo.getTodoListId())
                    .addParameter("done", todo.getIsDone())
                    .addParameter("created", todo.getCreatedAt())
                    .addParameter("updated", todo.getUpdatedAt())
                    .executeUpdate()
                    .getKey(Long.class);

            todo.setId(id);
        }
    }

    @Override
    public void update(Todo todo) {
        try (Connection conn = sql2o.open()) {
            constructUpdateQuery(conn, todo)
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

    private Query constructUpdateQuery(Connection connection, Todo todo) {
        Query query = getUpdateQuery(connection);
        addParamsToUpdate(query, todo);
        return query;
    }

    private Query getUpdateQuery(Connection connection) {
        return connection.createQuery("UPDATE todo " +
                "SET title = :title, is_done = :done, updated_at = :updated " +
                "WHERE todo_id = :id");
    }

    private Query addParamsToUpdate(Query query, Todo todo) {
        return query.addParameter("id", todo.getId())
                .addParameter("title", todo.getTitle())
                .addParameter("done", todo.getIsDone())
                .addParameter("updated", new Date());
    }
}
