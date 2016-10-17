package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.DatabaseConfig;
import com.dovydasvenckus.todo.helper.db.SqlFileExecutor;
import org.sql2o.Sql2o;

public class HSQLDConnector extends DatabaseConnector {
    public HSQLDConnector() throws ClassNotFoundException {
        super("org.hsqldb.jdbc.JDBCDriver");
        loadDriver();
    }

    @Override
    public Sql2o getInstance(DatabaseConfig databaseConfig) {
        Sql2o sql2o;
        if (databaseConfig.getUrl().isPresent() && databaseConfig.getUsername().isPresent()) {
            String password = databaseConfig.getPassword().isPresent() ? databaseConfig.getPassword().get() : null;
            sql2o = new Sql2o(databaseConfig.getUrl().get(), databaseConfig.getUsername().get(), password);
        } else {
            sql2o = new Sql2o("jdbc:hsqldb:mem:todoDb", "sa", "");
            createTables(sql2o);
        }
        return sql2o;
    }

    private void createTables(Sql2o sql2o) {
        SqlFileExecutor sqlFileExecutor = new SqlFileExecutor(sql2o);
        sqlFileExecutor.execute("/sql/hsqldb/create.sql");
    }

}
