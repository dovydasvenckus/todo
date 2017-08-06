package com.dovydasvenckus.todo;

import com.beust.jcommander.JCommander;
import com.dovydasvenckus.todo.auth.AuthConfigFactory;
import com.dovydasvenckus.todo.auth.AuthService;
import com.dovydasvenckus.todo.helper.cmd.options.CommandLineOptions;
import com.dovydasvenckus.todo.helper.db.DatabaseConfig;
import com.dovydasvenckus.todo.helper.db.connector.DatabaseConnector;
import com.dovydasvenckus.todo.helper.db.connector.DatabaseConnectorSelector;
import com.dovydasvenckus.todo.list.TodoListController;
import com.dovydasvenckus.todo.list.TodoListService;
import com.dovydasvenckus.todo.todo.TodoController;
import com.dovydasvenckus.todo.todo.TodoService;
import com.dovydasvenckus.todo.util.Controller;
import com.dovydasvenckus.todo.util.Service;
import org.pac4j.core.config.Config;
import org.pac4j.sparkjava.SecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static spark.Spark.*;

public class TodoApplication {
    private final static Logger logger = LoggerFactory.getLogger(TodoApplication.class);

    private static DatabaseConnector databaseConnector;
    private static DatabaseConfig databaseConfig;

    private static List<Controller> controllers = new ArrayList<>();
    private static List<Service> services = new ArrayList<>();

    private static Config securityConfig;
    private static AuthService authService;

    public static void main(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);
        port(options.getPort() != null ? new Integer(options.getPort()) : 8080);
        loadDatabaseConfig(options);

        staticFiles.location("/public");
        setupSecurityFilters(options);
        initModules();
        logger.info("Finished initialization");
    }

    private static void loadDatabaseConfig(CommandLineOptions options) {
        databaseConfig = new DatabaseConfig(options.getDbUrl(), options.getDbUser(), options.getDbPass());
    }

    private static void addService(Service service) {
        if (!findServiceByName(service.getName()).isPresent()) {
            services.add(service);
        }
    }

    private static Optional<Service> findServiceByName(String name) {
        return services.stream()
                .filter(service -> service.getName().equals(name))
                .findAny();
    }

    private static void setupServices(Supplier<Sql2o> dataSourceSupplier) {
        addService(new TodoListService(dataSourceSupplier.get()));
        addService(new TodoService(dataSourceSupplier.get(), (TodoListService) findServiceByName("TodoListService").get()));
    }

    private static void setupControllers() {
        controllers.add(new TodoController((TodoService) findServiceByName("TodoService").get()));
        controllers.add(new TodoListController(
                (TodoListService) findServiceByName("TodoListService").get(),
                (TodoService) findServiceByName("TodoService").get()));
        controllers.forEach(Controller::setupRoutes);
    }

    private static void setupSecurityFilters(CommandLineOptions options) {
        authService = new AuthService(options.getUser(), options.getPassword());
        securityConfig = new AuthConfigFactory(authService).build();

        before("/api/*", new SecurityFilter(securityConfig, "directBasicAuthClient", "dumbAuthorizer"));
    }

    private static void initModules() {
        try {
            databaseConnector = (new DatabaseConnectorSelector()).getConnectorInstance(databaseConfig);
            tryToConnectToDatabase();
            setupServices(() -> databaseConnector.getInstance(databaseConfig));
            setupControllers();
        } catch (ClassNotFoundException ex) {
            logger.error("DatabaseDriverEnum connector driver not found", ex);
        } catch (Sql2oException sql2oException) {
            logger.error("Error while trying to establish database connection", sql2oException);

            System.exit(-1);
        }
    }

    private static void tryToConnectToDatabase() throws Sql2oException {
        databaseConnector.getInstance(databaseConfig).open();
    }

}
