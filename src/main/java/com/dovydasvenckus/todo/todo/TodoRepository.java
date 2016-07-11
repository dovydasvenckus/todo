package com.dovydasvenckus.todo.todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Optional<Todo> find(Long id);

    List<Todo> listAll();

    List<Todo> listDone();

    List<Todo> listActive();

    void add(Todo todo);

    void update(Todo todo);

    void remove(Long id);

}
