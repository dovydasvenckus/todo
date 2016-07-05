package com.dovydasvenckus.todo.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoRepositoryImpl implements TodoRepository{
    private final List<Todo> todoList = new ArrayList<>();

    @Override
    public Optional<Todo> find(String uuid) {
        return todoList.stream()
                .filter(todo -> todo.getUuid().equals(uuid))
                .findAny();
    }

    @Override
    public List<Todo> listAll() {
        return todoList;
    }

    @Override
    public void add(Todo todo) {
        todoList.add(todo);
    }

    @Override
    public void update(String uuid, String title) {
        find(uuid)
                .ifPresent(todo -> todo.setDescription(title));
    }

    @Override
    public void remove(String uuid) {
        find(uuid)
                .ifPresent(todo -> todoList.remove(todo));
    }
}
