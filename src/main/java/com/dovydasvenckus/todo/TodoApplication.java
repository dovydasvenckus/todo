package com.dovydasvenckus.todo;

import com.dovydasvenckus.todo.todo.Todo;
import com.dovydasvenckus.todo.todo.TodoRepository;
import com.dovydasvenckus.todo.todo.TodoRepositoryImpl;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class TodoApplication {
    private static TodoRepository todoRepository = new TodoRepositoryImpl();

    public static void main(String[] args) {
        get("/add/:description", TodoApplication::addToDo);

        get("/do/:id", TodoApplication::doTask);

        get("/todo", (rq, rs) -> {
            Map<String, List<Todo>> model = new HashMap<>();
            model.put("todos", todoRepository.listAll());
            return new VelocityTemplateEngine().render(new ModelAndView(model, "templates/todo.vm"));
        });

    }

    private static String doTask(Request req, Response res) {
        String id = req.params(":id");
        todoRepository.find(id).ifPresent(todo -> todo.setIsDone(true));
        res.redirect("/todo");
        return null;
    }

    private static String addToDo(Request req, Response res) {
        String description = req.params(":description");
        todoRepository.add(new Todo(description));
        res.redirect("/todo");
        return null;
    }
}
