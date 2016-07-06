package com.dovydasvenckus.todo.todo;

import java.util.List;

public class TodoService {
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodos(String status) {
        List<Todo> result;
        switch (status) {
            case "active":
                result = todoRepository.listActive();
                break;
            case "complete":
                result = todoRepository.listDone();
                break;
            default:
                result = todoRepository.listAll();
        }
        return  result;
    }
}
