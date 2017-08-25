package com.dovydasvenckus.todo.utils.db.connectors;

import com.dovydasvenckus.todo.utils.db.DatabaseConfig;
import com.dovydasvenckus.todo.utils.db.init.DatabaseInitiator;
import com.dovydasvenckus.todo.utils.db.init.DatabaseInitiatorFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static com.dovydasvenckus.todo.utils.db.DatabaseDriver.H2;

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
            connection = getInMemoryDbConnection();
            initTableWithSeedData(connection);
            inMemoryDBInitialized = true;
        } else {
            connection = getInMemoryDbConnection();
        }

        return connection;
    }

    private void initTableWithSeedData(Connection connection) throws SQLException {
        DatabaseInitiator databaseInitiator = DatabaseInitiatorFactory.getDatabaseInitiator(H2, connection);
        databaseInitiator.createTables();
        databaseInitiator.initSeedData();
    }

    private Connection initH2BFromDbConfig(DatabaseConfig databaseConfig) throws SQLException {
        String password = databaseConfig.getPassword().isPresent() ? databaseConfig.getPassword().get() : null;
        return DriverManager.getConnection(databaseConfig.getUrl().get(), databaseConfig.getUsername().get(), password);
    }

    Connection getInMemoryDbConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:todo;DB_CLOSE_DELAY=-1;", "sa", "");
    }
}
