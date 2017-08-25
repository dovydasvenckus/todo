package com.dovydasvenckus.todo.list;

import com.dovydasvenckus.todo.todo.TodoService;
import com.dovydasvenckus.todo.utils.Controller;
import com.google.gson.Gson;
import spark.Route;

import java.util.Optional;

import static spark.Spark.get;
import static spark.Spark.post;

public class TodoListController implements Controller {

    private static String URL = "/api/list";

    private final Gson gson = new Gson();

    private final TodoService todoService;

    private final TodoListService todoListService;

    public TodoListController(TodoListService todoListService, TodoService todoService) {
        this.todoListService = todoListService;
        this.todoService = todoService;
    }

    @Override
    public void setupRoutes() {
        get(URL, "application/json", listTodoLists(), gson::toJson);
        get(URL + "/:list/todos", "application/json", listTodosFromList(), gson::toJson);
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

    private Route listTodoLists() {
        return (request, response) -> {
            response.header("Content-Encoding", "gzip");
            return todoListService.getLists();
        };
    }

    private Route listTodosFromList() {
        return (request, response) -> {
            try {
                Long id = Long.parseLong(request.params(":list"));
                Optional<TodoList> todoList = todoListService.findById(id);

                if (todoList.isPresent()) {
                    response.header("Content-Encoding", "gzip");
                    return todoService.getFromList(todoList.get());
                }

                response.status(404);
                return new Object();
            }
            catch (NumberFormatException nfe) {
                response.status(400);
                return new Object();
            }
        };
    }
}
