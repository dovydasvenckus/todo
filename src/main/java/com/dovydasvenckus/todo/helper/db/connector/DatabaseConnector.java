package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.DatabaseConfig;
import org.sql2o.Sql2o;

public abstract class DatabaseConnector {
    protected final String driverClassName;

    DatabaseConnector(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public abstract Sql2o getInstance(DatabaseConfig databaseConfig);

    protected void loadDriver() throws ClassNotFoundException {
        Class.forName(driverClassName);
    }
}
