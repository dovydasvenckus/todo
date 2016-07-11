package com.dovydasvenckus.todo.todo;

import java.util.List;
import java.util.Optional;

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

    public void toggleDo(Long id) {
        Optional<Todo> todo = todoRepository.find(id);
        todo.ifPresent(t -> {
            t.toggleDone();
            todoRepository.update(t);
        });
    }

    public void toggleAll() {
        List<Todo> todos = todoRepository.listAll();
        todos.forEach(todo -> {
            todo.toggleDone();
            todoRepository.update(todo);
        });
    }
}
