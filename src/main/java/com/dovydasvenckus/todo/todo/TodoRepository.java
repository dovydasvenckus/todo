package com.dovydasvenckus.todo.todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Optional<Todo> find(String uuid);

    List<Todo> listAll();

    void add(Todo todo);

    void update(String uuid, String title);

    void remove(String uuid);

}
