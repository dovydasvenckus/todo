package com.dovydasvenckus.todo.helper.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlFileExecutor {
    private final static Logger logger = LoggerFactory.getLogger(SqlFileExecutor.class);

    private Sql2o sql2o;

    public SqlFileExecutor(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void execute(String fileName) {
        String sqlScript = readSqlScript(fileName);
        List<String> statements = new ArrayList<>(Arrays.asList(sqlScript.split(";")));


        try (Connection con = sql2o.beginTransaction()) {
            statements.forEach(statement -> {
                logger.info("Executing SQL command: " + statement);
                con.createQuery(statement)
                        .executeUpdate();
            });

        }
    }

    private String readSqlScript(String fileName) {
        String script = null;
        try {
            script = readFile(fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        return script;
    }

    private String readFile(String fileName) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(SqlFileExecutor.class.getResourceAsStream(fileName)))) {
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }

}
