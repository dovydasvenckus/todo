package com.dovydasvenckus.todo;

import com.beust.jcommander.JCommander;
import com.dovydasvenckus.todo.helper.CommandLineOptions;
import com.dovydasvenckus.todo.helper.db.DbConnectionFactory;
import com.dovydasvenckus.todo.helper.db.SqlFileExecutor;
import com.dovydasvenckus.todo.todo.Todo;
import com.dovydasvenckus.todo.todo.TodoRepository;
import com.dovydasvenckus.todo.todo.TodoRepositoryImpl;
import com.dovydasvenckus.todo.todo.TodoService;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static spark.Spark.*;

public class TodoApplication {
    private static Sql2o sql2o;
    private static TodoRepository todoRepository;
    private static TodoService todoService;

    public static void main(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);
        initModules(options);
        staticFiles.location("/public");

        get("/", (req, res) -> renderTodos(req));

        get("/todos/:id/edit", (req, res) -> renderEditTodo(req));

        post("/todos", TodoApplication::addToDo);

        put("/todos/:id/toggle", TodoApplication::toggleTodo);

        put("/todos/toggle_all", TodoApplication::toggleAll);

        put("/todos/:id", TodoApplication::update);

        delete("/todos/:id", TodoApplication::deleteTodo);

    }

    private static void initModules(CommandLineOptions options) {
        sql2o = DbConnectionFactory.getInstance(options);

        if (options.getDbHost() == null) {
            SqlFileExecutor sqlFileExecutor = new SqlFileExecutor(sql2o);
            sqlFileExecutor.execute("sql/hsqldb/create.sql");
        }

        todoRepository = new TodoRepositoryImpl(sql2o);
        todoService = new TodoService(todoRepository);
    }


    private static String addToDo(Request req, Response res) {
        todoRepository.add(new Todo(req.queryParams("todo-title")));
        return renderTodos(req);
    }

    private static String toggleTodo(Request req, Response res) {
        todoService.toggleDo(Long.parseLong(req.params(":id")));
        return renderTodos(req);
    }

    private static String toggleAll(Request req, Response res) {
        todoService.toggleAll();
        return renderTodos(req);
    }

    private static String update(Request req, Response res) {
        Optional<Todo> todo = todoRepository.find(Long.parseLong(req.params(":id")));
        if (todo.isPresent()) {
            todo.get().setTitle(req.queryParams("todo-title"));
            todoRepository.update(todo.get());
        }
        return renderTodos(req);
    }

    private static String deleteTodo(Request req, Response res) {
        String id = req.params(":id");
        todoRepository.remove(Long.parseLong(id));
        return renderTodos(req);
    }

    private static String renderEditTodo(Request req) {
        return renderTemplate("templates/editTodo.vm",
                new HashMap() {{
                    put("todo", todoRepository.find(Long.parseLong(req.params(":id"))).get());
                }});
    }

    private static String renderTodos(Request req) {
        String statusStr = req.queryParams("status");
        List<Todo> todoList = todoService.getTodos(statusStr != null ? statusStr : "");
        Long activeCount = todoRepository.count(of(false));
        Map<String, Object> model = new HashMap<>();
        model.put("todos", todoList);
        model.put("allCompleted", todoRepository.count(empty()).equals(todoRepository.count(of(true))));
        model.put("filter", Optional.ofNullable(statusStr).orElse(""));
        model.put("activeCount", activeCount);
        model.put("anyActive", activeCount > 0);
        model.put("status", Optional.ofNullable(statusStr).orElse(""));

        if ("true".equals(req.queryParams("ic-request"))) {
            return renderTemplate("templates/todoList.vm", model);
        }
        return renderTemplate("templates/index.vm", model);
    }

    private static String renderTemplate(String template, Map model) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }
}
