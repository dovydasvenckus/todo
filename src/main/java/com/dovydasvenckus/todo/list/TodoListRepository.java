package com.dovydasvenckus.todo.list;

import java.util.Optional;

public interface TodoListRepository {

    Optional<TodoList> findInbox();

    Optional<TodoList> findById(Long id);

    void create(TodoList todoList);

}
