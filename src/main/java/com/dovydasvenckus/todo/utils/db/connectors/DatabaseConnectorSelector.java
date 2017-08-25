package com.dovydasvenckus.todo.utils.db.connectors;

import com.dovydasvenckus.todo.utils.db.DatabaseConfig;
import com.dovydasvenckus.todo.utils.db.DatabaseDriver;

import java.util.Arrays;
import java.util.List;

public class DatabaseConnectorSelector {

    private final List<DatabaseDriver> registeredDrivers = Arrays.asList(DatabaseDriver.values());

    public DatabaseConnector getConnectorInstance(DatabaseConfig databaseConfig) throws ClassNotFoundException {
        if (databaseConfig.getUrl().isPresent()) {
            DatabaseDriver driverUsedInJdbcUrl = registeredDrivers.stream()
                    .filter(db -> db.isUsingSameDriver(databaseConfig.getUrl().get()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("JDBC contains unknown driver"));
            return driverUsedInJdbcUrl.getConnector();
        }
        return new H2Connector();
    }
}
