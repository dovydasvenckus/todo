package com.dovydasvenckus.todo.todo;

import org.sql2o.Sql2o;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

public class TodoService {
    private TodoRepository todoRepository;

    public TodoService(Sql2o dbConnection) {
        this.todoRepository = new TodoRepositoryImpl(dbConnection);
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
        return result;
    }

    public Optional<Todo> find(Long id) {
        return todoRepository.find(id);
    }

    public Optional<Todo> create(CreateTodoDto createTodoDto) {
        if (createTodoDto.getTitle() != null) {
            Todo todo = new Todo(createTodoDto.getTitle());
            todoRepository.add(todo);
            return Optional.of(todo);
        }
        return empty();
    }

    public void delete(Long id) {
        todoRepository.remove(id);
    }

    public void toggleDo(Long id) {
        Optional<Todo> todo = todoRepository.find(id);
        todo.ifPresent(t -> {
            t.toggleDone();
            todoRepository.update(t);
        });
    }
}
