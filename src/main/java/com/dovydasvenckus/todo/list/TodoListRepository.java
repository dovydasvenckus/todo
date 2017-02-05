package com.dovydasvenckus.todo.list;

import java.util.List;
import java.util.Optional;

public interface TodoListRepository {

    Optional<TodoList> findInbox();

    Optional<TodoList> findById(Long id);

    List<TodoList> listAll();

    void create(TodoList todoList);

}
