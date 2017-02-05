package com.dovydasvenckus.todo.list;

import com.dovydasvenckus.todo.util.Service;
import org.sql2o.Sql2o;

import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class TodoListService implements Service {
    private TodoListRepository todoListRepository;

    public TodoListService(Sql2o dataSource) {
        this.todoListRepository = new TodoListRepositoryImpl(dataSource);
    }

    public Optional<TodoList> getInbox() {
        return todoListRepository.findInbox();
    }

    public Optional<TodoList> create(CreateTodoListDto createTodoListDto) {
        if (!isNullOrEmpty(createTodoListDto.getTitle())) {
            TodoList todoList = new TodoList(createTodoListDto.getTitle());
            todoListRepository.create(todoList);

            return of(todoList);
        }
        return empty();
    }
}