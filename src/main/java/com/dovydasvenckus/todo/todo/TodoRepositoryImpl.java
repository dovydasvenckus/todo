package com.dovydasvenckus.todo.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class TodoRepositoryImpl implements TodoRepository {
    private final List<Todo> todoList = new ArrayList<>();

    @Override
    public Optional<Todo> find(String uuid) {
        return todoList.stream()
                .filter(todo -> todo.getId().equals(uuid))
                .findAny();
    }

    @Override
    public List<Todo> listAll() {
        return todoList;
    }

    @Override
    public List<Todo> listDone() {
        return todoList.stream()
                .filter(Todo::getIsDone)
                .collect(toList());
    }

    @Override
    public List<Todo> listActive() {
        return todoList.stream()
                .filter(todo -> !todo.getIsDone())
                .collect(toList());
    }

    @Override
    public void add(Todo todo) {
        todoList.add(todo);
    }

    @Override
    public void update(String uuid, String title) {
        find(uuid)
                .ifPresent(todo -> {
                            if (!todo.getTitle().trim().isEmpty())
                                todo.setTitle(title);
                            else remove(uuid);
                        }
                );
    }

    @Override
    public void remove(String uuid) {
        find(uuid)
                .ifPresent(todoList::remove);
    }
}
