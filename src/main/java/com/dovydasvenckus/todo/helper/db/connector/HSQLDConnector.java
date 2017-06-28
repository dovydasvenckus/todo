package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.DatabaseConfig;
import com.dovydasvenckus.todo.helper.db.SqlFileExecutor;
import org.sql2o.Sql2o;

public class HSQLDConnector extends DatabaseConnector {

    private static boolean inMemoryDBInitialized = false;

    public HSQLDConnector() throws ClassNotFoundException {
        super("org.hsqldb.jdbc.JDBCDriver");
        loadDriver();
    }

    @Override
    public Sql2o getInstance(DatabaseConfig databaseConfig) {
        Sql2o sql2o;

        if (databaseConfig.getUrl().isPresent() && databaseConfig.getUsername().isPresent()) {
            sql2o = initHSQLDBFromDbConfig(databaseConfig);
        } else {
            sql2o = createAndInitInMemoryDB();
        }
        return sql2o;
    }

    private Sql2o createAndInitInMemoryDB() {
        Sql2o sql2o;
        if (!inMemoryDBInitialized) {
            sql2o = createInMemoryDB();
            createTables(sql2o);
            inMemoryDBInitialized = true;
        } else {
            sql2o = createInMemoryDB();
        }

        return sql2o;
    }

    private Sql2o initHSQLDBFromDbConfig(DatabaseConfig databaseConfig) {
        String password = databaseConfig.getPassword().isPresent() ? databaseConfig.getPassword().get() : null;
        return new Sql2o(databaseConfig.getUrl().get(), databaseConfig.getUsername().get(), password);
    }

    private Sql2o createInMemoryDB() {
        return new Sql2o("jdbc:hsqldb:mem:todoDb", "sa", "");
    }

    private void createTables(Sql2o sql2o) {
        SqlFileExecutor sqlFileExecutor = new SqlFileExecutor(sql2o);
        sqlFileExecutor.execute("/sql/hsqldb/create.sql");
    }

}
