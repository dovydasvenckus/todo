package com.dovydasvenckus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlFileExecutor {
    private final static Logger logger = LoggerFactory.getLogger(SqlFileExecutor.class);

    private Connection connection;

    public SqlFileExecutor(Connection connection) {
        this.connection = connection;
    }

    public void execute(String fileName) throws SQLException {
        String sqlScript = readSqlScript(fileName);
        List<String> statements = new ArrayList<>(Arrays.asList(sqlScript.split(";")));


        for (String statement : statements) {
            try (Statement stm = connection.createStatement()) {
                logger.info("Executing SQL command: " + statement);
                stm.execute(statement);
            }

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
