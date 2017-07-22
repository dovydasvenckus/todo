package com.dovydasvenckus.todo.list;

import com.dovydasvenckus.todo.util.Service;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Optional;

import static com.dovydasvenckus.todo.util.StringUtils.isNullOrEmpty;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class TodoListService implements Service {

    private final TodoListRepository todoListRepository;

    public TodoListService(Sql2o dataSource) {
        this.todoListRepository = new TodoListRepositoryImpl(dataSource);
    }

    public Optional<TodoList> findById(long id) {
        return todoListRepository.findById(id);
    }

    public Optional<TodoList> getInbox() {
        return todoListRepository.findInbox();
    }

    public List<TodoList> getLists() {
        return todoListRepository.listAll();
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
