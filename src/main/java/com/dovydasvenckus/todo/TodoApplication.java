package com.dovydasvenckus.todo;

import com.beust.jcommander.JCommander;
import com.dovydasvenckus.todo.helper.cmd.options.CommandLineOptions;
import com.dovydasvenckus.todo.helper.db.DatabaseConfig;
import com.dovydasvenckus.todo.helper.db.DbConnectionFactory;
import com.dovydasvenckus.todo.helper.db.SqlFileExecutor;
import com.dovydasvenckus.todo.todo.TodoController;
import com.dovydasvenckus.todo.util.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.port;

public class TodoApplication {
    private final static Logger logger = LoggerFactory.getLogger(TodoApplication.class);

    private static Sql2o sql2o;
    private static List<Controller> controllers = new ArrayList<>();
    private static DatabaseConfig databaseConfig;

    public static void main(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);
        port(options.getPort() != null ? new Integer(options.getPort()) : 8080);
        loadDatabaseConfig(options);

        initModules();
        logger.info("Finished initialization");
    }

    private static void loadDatabaseConfig(CommandLineOptions options) {
        databaseConfig = new DatabaseConfig(options.getDbUrl(), options.getDbUser(), options.getDbPass());
    }

    private static void setupControllers(Sql2o dbConnection) {
        controllers.add(new TodoController(dbConnection));
        controllers.forEach(Controller::setupRoutes);
    }

    private static void initModules() {
        sql2o = DbConnectionFactory.getInstance(databaseConfig);

        if (!databaseConfig.getUrl().isPresent()) {
            createInMemoryTables();
        }

        setupControllers(sql2o);
    }

    private static void createInMemoryTables() {
        SqlFileExecutor sqlFileExecutor = new SqlFileExecutor(sql2o);
        sqlFileExecutor.execute("/sql/hsqldb/create.sql");
    }
}
