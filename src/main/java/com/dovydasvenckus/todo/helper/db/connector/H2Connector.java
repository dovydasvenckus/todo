package com.dovydasvenckus.todo.helper.db.connector;

import com.dovydasvenckus.todo.helper.db.DatabaseConfig;
import com.dovydasvenckus.todo.helper.db.SqlFileExecutor;
import org.sql2o.Sql2o;

public class H2Connector extends DatabaseConnector {

    private static boolean inMemoryDBInitialized = false;

    public H2Connector() throws ClassNotFoundException {
        super("org.h2.Driver");
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
        return new Sql2o("jdbc:h2:mem:todo;DB_CLOSE_DELAY=-1;", "sa", "");
    }

    private void createTables(Sql2o sql2o) {
        SqlFileExecutor sqlFileExecutor = new SqlFileExecutor(sql2o);
        sqlFileExecutor.execute("/sql/hsqldb/create.sql");
        sqlFileExecutor.execute("/sql/hsqldb/seed.sql");
    }

}
