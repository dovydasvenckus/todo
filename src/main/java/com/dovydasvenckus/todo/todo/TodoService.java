package com.dovydasvenckus.todo.todo;

import com.dovydasvenckus.todo.list.TodoList;
import com.dovydasvenckus.todo.list.TodoListService;
import com.dovydasvenckus.todo.util.Service;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

public class TodoService implements Service {
    private final TodoListService todoListService;
    private final TodoRepository todoRepository;

    public TodoService(Sql2o dataSource, TodoListService todoListService) {
        this.todoRepository = new TodoRepositoryImpl(dataSource);
        this.todoListService = todoListService;
    }

    public List<Todo> getTodos(TodoStateFilter filter) {
        switch (filter) {
            case NOT_DONE:
                return todoRepository.listActive();
            case DONE:
                return todoRepository.listDone();
            case ALL:
                return todoRepository.listAll();
            default:
                throw new IllegalArgumentException();
        }
    }

    public List<Todo> getFromList(TodoList todoList) {
        return todoRepository.list(todoList);
    }

    public Optional<Todo> find(Long id) {
        return todoRepository.find(id);
    }

    public Optional<Todo> create(CreateTodoDto createTodoDto) {
        if (createTodoDto.getTitle() != null) {

            Todo todo = new Todo(createTodoDto.getTitle(), getTodoListId(createTodoDto));

            todoRepository.add(todo);
            return Optional.of(todo);
        }
        return empty();
    }

    public void delete(Long id) {
        todoRepository.remove(id);
    }

    public void toggleDo(Long id) {
        Optional<Todo> todo = todoRepository.find(id);
        todo.ifPresent(t -> {
            t.toggleDone();
            todoRepository.update(t);
        });
    }

    private Long getTodoListId(CreateTodoDto createTodoDto) {
        return createTodoDto.getTodoListId() == null ?
                todoListService.getInbox().map(TodoList::getId).orElse(null) : createTodoDto.getTodoListId();
    }
}
