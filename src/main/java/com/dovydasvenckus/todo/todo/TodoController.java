package com.dovydasvenckus.todo.todo;

import com.dovydasvenckus.todo.util.Controller;
import com.google.gson.Gson;
import org.sql2o.Sql2o;
import spark.Route;

import static spark.Spark.*;

public class TodoController implements Controller {
    private static String URL = "/api/todo";

    protected final Gson gson = new Gson();

    protected final TodoService todoService;

    public TodoController(Sql2o dbConnection) {
        this.todoService = new TodoService(dbConnection);
    }

    @Override
    public void setupRoutes() {
        get(URL, "application/json", (request, response) -> todoService.getTodos(""), gson::toJson);
        get(URL + "/:id", "application/json", findTodo(), gson::toJson);
        post(URL, "application/json", createTodo());
        post(URL + "/toggle/:id", "application/json", toggle());
        delete(URL + "/:id", "application/json", deleteTodo());
    }

    private Route findTodo() {
        return (request, response) -> {
            try {
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
            if (todoService.create(todo).isPresent()) {
                response.status(201);
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
