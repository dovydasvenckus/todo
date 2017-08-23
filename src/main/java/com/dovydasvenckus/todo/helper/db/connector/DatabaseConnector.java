package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Optional;

public abstract class DatabaseConnector {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);

    private final String driverClassName;

    DatabaseConnector(String driverClassName) {
        this.driverClassName = driverClassName;
        logger.info("Loading {} database driver", driverClassName);
    }

    public abstract Optional<Connection> getInstance(DatabaseConfig databaseConfig);

    void loadDriver() throws ClassNotFoundException {
        Class.forName(driverClassName);
    }
}
