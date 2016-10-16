package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.DatabaseConfig;
import org.sql2o.Sql2o;

public class PostgresqlConnector implements DatabaseConnector {

    public PostgresqlConnector() throws ClassNotFoundException {
        loadDriver();
    }

    @Override
    public Sql2o getInstance(DatabaseConfig databaseConfig) throws IllegalArgumentException {
        if (isDataConfigNotBlank(databaseConfig)) {
            return new Sql2o(databaseConfig.getUrl().get(), databaseConfig.getUsername().get(), databaseConfig.getPassword().get());
        } else throw new IllegalArgumentException("Missing database config parameters.");
    }

    private void loadDriver() throws ClassNotFoundException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
    }

    private boolean isDataConfigNotBlank(DatabaseConfig config) {
        return config.getUrl().isPresent() && config.getUsername().isPresent() && config.getPassword().isPresent();
    }
}
