package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.SqlFileExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseUtils {

    static void createTables(Connection connection) throws SQLException {
        SqlFileExecutor sqlFileExecutor = new SqlFileExecutor(connection);
        sqlFileExecutor.execute("/sql/hsqldb/create.sql");
    }

    static void initSeedData(Connection connection) throws SQLException {
        SqlFileExecutor sqlFileExecutor = new SqlFileExecutor(connection);
        sqlFileExecutor.execute("/sql/hsqldb/seed.sql");
    }

    static Connection getInMemoryDbConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:todo;DB_CLOSE_DELAY=-1;", "sa", "");
    }
}
