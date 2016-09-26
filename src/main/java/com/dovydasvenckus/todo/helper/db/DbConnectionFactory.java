package com.dovydasvenckus.todo.helper.db;

import com.dovydasvenckus.todo.helper.cmd.options.CommandLineOptions;
import org.sql2o.Sql2o;

public class DbConnectionFactory {

    public static Sql2o getInstance(CommandLineOptions options) {
        Sql2o sql2o;
        if (options.getDbHost() != null) {
            sql2o = new Sql2o(options.getDbHost(), options.getDbUser(), options.getDbPassword());
        } else {
            sql2o = new Sql2o("jdbc:hsqldb:mem:todoDb", "sa", "");
        }
        return sql2o;
    }
}
