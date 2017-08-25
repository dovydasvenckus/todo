package com.dovydasvenckus.todo.utils.db.connectors;

import com.dovydasvenckus.todo.utils.db.DatabaseConfig;
import com.dovydasvenckus.todo.utils.db.init.DatabaseInitiatorFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static com.dovydasvenckus.todo.utils.db.DatabaseDriver.H2;

public class AlwaysInMemoryH2Connector extends DatabaseConnector {

    public AlwaysInMemoryH2Connector() throws ClassNotFoundException {
        super("org.h2.Driver");
        loadDriver();
    }

    @Override
    public Optional<Connection> getInstance(DatabaseConfig databaseConfig) {
        try {
            Connection connection = getInMemoryDbConnection();
            DatabaseInitiatorFactory.getDatabaseInitiator(H2, connection).createTables();

            return Optional.of(connection);
        } catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    Connection getInMemoryDbConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:todo;DB_CLOSE_DELAY=-1;", "sa", "");
    }
}
