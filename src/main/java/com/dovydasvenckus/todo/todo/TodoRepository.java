package com.dovydasvenckus.todo.todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Optional<Todo> find(String uuid);

    List<Todo> listAll();

    List<Todo> listDone();

    List<Todo> listActive();

    void add(Todo todo);

    void update(String uuid, String title);

    void remove(String uuid);

}
