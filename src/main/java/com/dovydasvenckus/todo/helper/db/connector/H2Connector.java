package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class H2Connector extends DatabaseConnector {

    private static boolean inMemoryDBInitialized = false;

    public H2Connector() throws ClassNotFoundException {
        super("org.h2.Driver");
        loadDriver();
    }

    @Override
    public Optional<Connection> getInstance(DatabaseConfig databaseConfig) {
        Connection connection;

        try {
            if (databaseConfig.getUrl().isPresent() && databaseConfig.getUsername().isPresent()) {
                connection = initH2BFromDbConfig(databaseConfig);
            } else {
                connection = createAndInitInMemoryDB();
            }
            return Optional.of(connection);
        } catch (SQLException sqlConnection) {
            return Optional.empty();
        }
    }

    private Connection createAndInitInMemoryDB() throws SQLException {
        Connection connection;
        if (!inMemoryDBInitialized) {
            connection = H2DatabaseUtils.getInMemoryDbConnection();
            H2DatabaseUtils.createTables(connection);
            H2DatabaseUtils.initSeedData(connection);
            inMemoryDBInitialized = true;
        } else {
            connection = H2DatabaseUtils.getInMemoryDbConnection();
        }

        return connection;
    }

    private Connection initH2BFromDbConfig(DatabaseConfig databaseConfig) throws SQLException {
        String password = databaseConfig.getPassword().isPresent() ? databaseConfig.getPassword().get() : null;
        return DriverManager.getConnection(databaseConfig.getUrl().get(), databaseConfig.getUsername().get(), password);
    }

}
