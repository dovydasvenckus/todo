package com.dovydasvenckus.todo.todo;

import com.dovydasvenckus.todo.list.TodoList;

import java.util.List;
import java.util.Optional;

public interface TodoRepository{
    Optional<Todo> find(Long id);

    List<Todo> listAll();

    List<Todo> listDone();

    List<Todo> listActive();

    List<Todo> list(TodoList todoList);

    Long count(Optional<Boolean> isDone);

    void batchUpdate(List<Todo> todoList);

    void add(Todo todo);

    void update(Todo todo);

    void remove(Long id);

}
