package com.dovydasvenckus.todo.helper.db;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class SqlFileExecutor {
    private Sql2o sql2o;

    public SqlFileExecutor(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void execute(String fileName) {
        String sqlScript = readSqlScript(fileName);
        List<String> statements = new ArrayList<>(Arrays.asList(sqlScript.split(";")));

        try (Connection con = sql2o.beginTransaction()) {
            statements.forEach(statement ->
                    con.createQuery(statement)
                            .executeUpdate());
        }
    }

    private String readSqlScript(String fileName) {
        String script = null;
        try {
            script = new String(readAllBytes(get(ClassLoader.getSystemResource(fileName).toURI())));
        } catch (IOException | URISyntaxException | OutOfMemoryError ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return script;
    }

}
