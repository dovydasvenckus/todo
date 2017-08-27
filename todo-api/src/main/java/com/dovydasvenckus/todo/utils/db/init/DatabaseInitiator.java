package com.dovydasvenckus.todo.utils.db.init;

import com.dovydasvenckus.todo.utils.db.DatabaseDriver;
import com.dovydasvenckus.jdbc.SqlFileExecutor;

import java.sql.SQLException;

public class DatabaseInitiator {

    private final DatabaseDriver databaseDriver;

    private final SqlFileExecutor sqlFileExecutor;


    public DatabaseInitiator(DatabaseDriver databaseDriver, SqlFileExecutor sqlFileExecutor) {
        this.databaseDriver = databaseDriver;
        this.sqlFileExecutor = sqlFileExecutor;
    }

    public void createTables() throws SQLException {
        sqlFileExecutor.execute(getCreateScriptPath());
    }

    public void initSeedData() throws SQLException {
        sqlFileExecutor.execute(getSeedScriptPath());
    }

    private String getCreateScriptPath() {
        return getFilePath("create.sql");
    }

    private String getSeedScriptPath() {
        return getFilePath("seed.sql");
    }

    private String getFilePath(String file) {
        return String.format("/sql/%s/%s",
                databaseDriver.toString().toLowerCase(),
                file.toLowerCase()
        );
    }
}
