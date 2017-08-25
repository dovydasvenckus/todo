package com.dovydasvenckus.todo.utils.db.init;

import com.dovydasvenckus.todo.utils.db.DatabaseDriver;
import com.dovydasvenckus.todo.utils.db.sql.SqlFileExecutor;

import java.sql.Connection;

public class DatabaseInitiatorFactory {

    public static DatabaseInitiator getDatabaseInitiator(DatabaseDriver databaseDriver, Connection connection) {
        SqlFileExecutor sqlFileExecutor = new SqlFileExecutor(connection);

        return new DatabaseInitiator(databaseDriver, sqlFileExecutor);
    }
}
