package com.dovydasvenckus.todo;

import com.beust.jcommander.JCommander;
import com.dovydasvenckus.todo.helper.CommandLineOptions;
import com.dovydasvenckus.todo.helper.db.DbConnectionFactory;
import com.dovydasvenckus.todo.helper.db.DbFromEnv;
import com.dovydasvenckus.todo.helper.db.SqlFileExecutor;
import com.dovydasvenckus.todo.todo.TodoController;
import com.dovydasvenckus.todo.util.Controller;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.port;

public class TodoApplication {
    private static Sql2o sql2o;
    private static List<Controller> controllers = new ArrayList<>();

    public static void main(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);
        port(options.getPort() != null ? new Integer(options.getPort()) : 8080);

        initModules(options);
    }

    private static void setupControllers(Sql2o dbConnection) {
        controllers.add(new TodoController(dbConnection));
        controllers.forEach(Controller::setupRoutes);
    }

    private static void initModules(CommandLineOptions options) {
        if (isProdEnvironment(options)) {
            DbFromEnv dbFromEnv = new DbFromEnv();
            sql2o = dbFromEnv.getInstance();
        } else {
            sql2o = DbConnectionFactory.getInstance(options);
        }

        if (!isProdEnvironment(options)) {
            createInMemoryTables(options);
        }

        setupControllers(sql2o);
    }

    private static void createInMemoryTables(CommandLineOptions options) {
        if ((options.getDbHost() == null)) {
            SqlFileExecutor sqlFileExecutor = new SqlFileExecutor(sql2o);
            sqlFileExecutor.execute("/sql/hsqldb/create.sql");
        }
    }

    private static boolean isProdEnvironment(CommandLineOptions options) {
        return "prod".equalsIgnoreCase(options.getEnv());
    }
}
