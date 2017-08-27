package com.dovydasvenckus.todo.utils.db.connectors;

import com.dovydasvenckus.todo.utils.db.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class PostgresqlConnector extends DatabaseConnector {

    public PostgresqlConnector() throws ClassNotFoundException {
        super("org.postgresql.Driver");
        loadDriver();
    }

    @Override
    public Optional<Connection> getInstance(DatabaseConfig databaseConfig) throws IllegalArgumentException {
        if (isDataConfigNotBlank(databaseConfig)) {
            try {
                return Optional.of(getConnection(databaseConfig));
            } catch (SQLException ex) {
                return Optional.empty();
            }
        } else throw new IllegalArgumentException("Missing database config parameters.");
    }

    private Connection getConnection(DatabaseConfig databaseConfig) throws SQLException {
        return DriverManager.getConnection(databaseConfig.getUrl().get(), databaseConfig.getUsername().get(), databaseConfig.getPassword().get());
    }

    private boolean isDataConfigNotBlank(DatabaseConfig config) {
        return config.getUrl().isPresent() && config.getUsername().isPresent() && config.getPassword().isPresent();
    }
}
