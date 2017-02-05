package com.dovydasvenckus.todo.list;

import com.dovydasvenckus.todo.util.Controller;
import com.google.gson.Gson;
import spark.Route;

import java.util.Optional;

import static spark.Spark.post;

public class TodoListController implements Controller {

    private static String URL = "/api/list";

    private final Gson gson = new Gson();

    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @Override
    public void setupRoutes() {
        post(URL, "application/json", createTodoList());
    }

    private Route createTodoList() {
        return (request, response) -> {
            CreateTodoListDto todo = gson.fromJson(request.body(), CreateTodoListDto.class);

            Optional<TodoList> todoList = todoListService.create(todo);
            if (todoList.isPresent()) {
                response.status(201);
                response.header("location", URL + "/" + todoList.get().getId());
                response.type("text/plain");
            } else response.status(400);
            return "";
        };
    }
}
