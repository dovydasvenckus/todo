package com.dovydasvenckus.todo.helper.db;

import org.sql2o.Sql2o;

public class DbConnectionFactory {

    public static Sql2o getInstance(DatabaseConfig databaseConfig) {
        Sql2o sql2o;
        if (databaseConfig.getUrl().isPresent()) {
            String password = databaseConfig.getPassword().isPresent() ? databaseConfig.getPassword().get() : null;
            sql2o = new Sql2o(databaseConfig.getUrl().get(), databaseConfig.getUsername().get(), password);
        } else {
            sql2o = new Sql2o("jdbc:hsqldb:mem:todoDb", "sa", "");
        }
        return sql2o;
    }
}
