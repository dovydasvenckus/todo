package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.DatabaseConfig;
import org.sql2o.Sql2o;

public interface DatabaseConnector {

    Sql2o getInstance(DatabaseConfig databaseConfig);
}
