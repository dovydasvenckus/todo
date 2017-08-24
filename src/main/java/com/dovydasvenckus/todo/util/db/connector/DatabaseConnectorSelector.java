package com.dovydasvenckus.todo.util.db.connector;

import com.dovydasvenckus.todo.util.db.DatabaseConfig;

import java.util.Arrays;
import java.util.List;

public class DatabaseConnectorSelector {

    private final List<DatabaseDriverEnum> registeredDrivers = Arrays.asList(DatabaseDriverEnum.values());

    public DatabaseConnector getConnectorInstance(DatabaseConfig databaseConfig) throws ClassNotFoundException {
        if (databaseConfig.getUrl().isPresent()) {
            DatabaseDriverEnum driverUsedInJdbcUrl = registeredDrivers.stream()
                    .filter(db -> db.isUsingSameDriver(databaseConfig.getUrl().get()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("JDBC contains unknown driver"));
            return driverUsedInJdbcUrl.getConnector();
        }
        return new H2Connector();
    }
}
