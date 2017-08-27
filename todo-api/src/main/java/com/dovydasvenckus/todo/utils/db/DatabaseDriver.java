package com.dovydasvenckus.todo.utils.db;

import com.dovydasvenckus.todo.utils.db.connectors.DatabaseConnector;
import com.dovydasvenckus.todo.utils.db.connectors.H2Connector;
import com.dovydasvenckus.todo.utils.db.connectors.PostgresqlConnector;

public enum DatabaseDriver {

    H2("h2"),
    POSTGRESQL("postgresql");

    private final String driverName;

    DatabaseDriver(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String toString() {
        return driverName;
    }

    public boolean isUsingSameDriver(String jdbcUrl) {
        return jdbcUrl.contains("jdbc:" + driverName);
    }

    public DatabaseConnector getConnector() throws ClassNotFoundException {
        switch (this) {
            case H2:
                return new H2Connector();
            case POSTGRESQL:
                return new PostgresqlConnector();
        }
        return null;
    }
}
