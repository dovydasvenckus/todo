package com.dovydasvenckus.todo.helper.db;

import org.sql2o.Sql2o;

public class DbFromEnv {
    private String jdbcUrl;
    private String username;
    private String password;

    public DbFromEnv() {
        jdbcUrl = System.getenv("todo_db_jdbc");
        username = System.getenv("todo_db_username");
        password = System.getenv("todo_db_password");
    }

    public Sql2o getInstance() {
        return isParamsInitialized() ? new Sql2o(jdbcUrl, username, password) : null;
    }

    private boolean isParamsInitialized() {
        return (jdbcUrl != null) && (username != null) && (password != null);
    }
}
