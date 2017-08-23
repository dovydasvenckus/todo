package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class AlwaysInMemoryH2Connector extends DatabaseConnector {

    public AlwaysInMemoryH2Connector() throws ClassNotFoundException {
        super("org.h2.Driver");
        loadDriver();
    }

    @Override
    public Optional<Connection> getInstance(DatabaseConfig databaseConfig) {
        try {
            Connection connection = H2DatabaseUtils.getInMemoryDbConnection();
            H2DatabaseUtils.createTables(connection);

            return Optional.of(connection);
        } catch (SQLException sqlException) {
            return Optional.empty();
        }
    }
}
