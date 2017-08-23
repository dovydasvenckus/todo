package com.dovydasvenckus.todo.list

import com.dovydasvenckus.todo.common.RepositorySpec
import com.dovydasvenckus.todo.util.sql.mapping.ResultSetToListMapper

import java.sql.ResultSet

class TodoListRepositorySpec extends RepositorySpec {

    TodoListRepository todoListRepository
    ResultSetToListMapper<TodoList> mapper = new ResultSetToListMapper<>(new TodoListMapper())

    def setup() {
        todoListRepository = new TodoListRepositoryImpl(connection)
    }

    def 'should insert new todo list'() {
        given:
            TodoList todoList = new TodoList('My todo')

        when:
            todoListRepository.create(todoList)

        then:
            List<TodoList> todoLists = getAllTodoLists()
            todoLists.size() == 1
            todoLists.get(0).title == 'My todo'
    }

    def 'should find index'() {
        given:
            TodoList todoList = new TodoList('Inbox')
            todoListRepository.create(todoList)

        when:
            Optional<TodoList> inbox = todoListRepository.findInbox()

        then:
            inbox.get().title == 'Inbox'
    }

    def 'should list all todo lists'() {
        given:
            todoListRepository.create(new TodoList('test1'))
            todoListRepository.create(new TodoList('test2'))
            todoListRepository.create(new TodoList('test3'))

        when:
            List<TodoList> todoLists = getAllTodoLists()

        then:
            todoLists.size() == 3
    }

    def 'should return empty list, when no todo lists are created'() {
        expect:
            getAllTodoLists().size() == 0
    }

    def 'should find list by id'() {
        given:
            todoListRepository.create(new TodoList('test1'))
            todoListRepository.create(new TodoList('test2'))

        when:
            Optional<TodoList> todoList = todoListRepository.findById(2)

        then:
            todoList.get().title == 'test2'
    }

    List<TodoList> getAllTodoLists() {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM list")
        return mapper.getListOfResults(resultSet)
    }

}
