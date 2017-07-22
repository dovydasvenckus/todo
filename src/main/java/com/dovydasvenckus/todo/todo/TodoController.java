package com.dovydasvenckus.todo.todo;

import com.dovydasvenckus.todo.util.Controller;
import com.google.gson.Gson;
import spark.Route;

import java.util.Optional;

import static spark.Spark.*;

public class TodoController implements Controller {
    private static String URL = "/api/todo";

    private final Gson gson = new Gson();

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public void setupRoutes() {
        get(URL, "application/json", listTodos(), gson::toJson);
        get(URL + "/:id", "application/json", findTodo(), gson::toJson);
        post(URL, "application/json", createTodo());
        post(URL + "/toggle/:id", "application/json", toggle());
        delete(URL + "/:id", "application/json", deleteTodo());
        options(URL, ((request, response) -> {
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
            response.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            return "OK";
        }));
    }

    private Route listTodos() {
        return (request, response) -> {
            response.header("Content-Encoding", "gzip");
            return todoService
                    .getTodos(TodoStateFilter.getStateFilter(request.queryParams("done")));
        };
    }

    private Route findTodo() {
        return (request, response) -> {
            try {
                response.header("Content-Encoding", "gzip");
                Long id = Long.parseLong(request.params(":id"));
                return todoService.find(id);
            } catch (NumberFormatException nfe) {
                response.status(400);
                return new Object();
            }
        };
    }

    private Route createTodo() {
        return (request, response) -> {
            CreateTodoDto todo = gson.fromJson(request.body(), CreateTodoDto.class);

            Optional<Todo> createdTodo = todoService.create(todo);

            if (createdTodo.isPresent()) {
                response.status(201);
                response.header("location", URL + "/" + createdTodo.get().getId());
                response.type("text/plain");
            } else response.status(400);
            return "";
        };
    }

    private Route deleteTodo() {
        return (request, response) -> {
            try {
                Long id = Long.parseLong(request.params(":id"));
                todoService.delete(id);
            } catch (NumberFormatException nfe) {
                response.status(400);
            }
            return "";
        };
    }

    private Route toggle() {
        return (request, response) -> {
            try {
                Long id = Long.parseLong(request.params(":id"));
                todoService.toggleDo(id);
                return "";
            } catch (NumberFormatException nfe) {
                response.status(400);
                return "";
            }
        };
    }
}
