package com.dovydasvenckus.todo;

import com.dovydasvenckus.todo.todo.Todo;
import com.dovydasvenckus.todo.todo.TodoRepository;
import com.dovydasvenckus.todo.todo.TodoRepositoryImpl;
import com.dovydasvenckus.todo.todo.TodoService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static spark.Spark.*;

public class TodoApplication {
    private static TodoRepository todoRepository = new TodoRepositoryImpl();
    private static TodoService todoService = new TodoService(todoRepository);

    public static void main(String[] args) {
        staticFiles.location("/public");

        get("/", (req, res) -> renderTodos(req));

        get("/todos/:id/edit", (req, res) -> renderEditTodo(req));

        post("/todos", TodoApplication::addToDo);

        put("/todos/:id/toggle", TodoApplication::toggleTodo);

        put("/todos/toggle_all", TodoApplication::toggleAll);

        put("/todos/:id", TodoApplication::update);

        delete("/todos/:id", TodoApplication::deleteTodo);

    }

    private static String addToDo(Request req, Response res) {
        todoRepository.add(new Todo(req.queryParams("todo-title")));
        return renderTodos(req);
    }

    private static String toggleTodo(Request req, Response res) {
        todoRepository.find(req.params(":id")).ifPresent(Todo::toggleDone);
        return renderTodos(req);
    }

    private static String toggleAll(Request req, Response res) {
        todoRepository.listAll().stream()
                .forEach(Todo::toggleDone);
        return renderTodos(req);
    }

    private static String update(Request req, Response res) {
        todoRepository.update(req.params("id"), req.queryParams("todo-title"));
        return renderTodos(req);
    }

    private static String deleteTodo(Request req, Response res) {
        String id = req.params(":id");
        todoRepository.remove(id);
        return renderTodos(req);
    }

    private static String renderEditTodo(Request req) {
        return renderTemplate("templates/editTodo.vm",
                new HashMap(){{ put("todo", todoRepository.find(req.params(":id")).get()); }});
    }

    private static String renderTodos(Request req) {
        String statusStr = req.queryParams("status");
        Map<String, Object> model = new HashMap<>();
        model.put("todos", todoService.getTodos(statusStr != null ? statusStr : ""));
        model.put("allCompleted", todoRepository.listAll().size() == todoRepository.listDone().size());
        model.put("filter", Optional.ofNullable(statusStr).orElse(""));
        model.put("activeCount", todoRepository.listActive().size());
        model.put("anyActive", todoRepository.listActive().size() > 0);
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
