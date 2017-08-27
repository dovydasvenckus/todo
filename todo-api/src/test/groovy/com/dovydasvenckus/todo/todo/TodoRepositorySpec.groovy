package com.dovydasvenckus.todo.todo

import com.dovydasvenckus.todo.common.RepositorySpec
import com.dovydasvenckus.todo.list.TodoList
import com.dovydasvenckus.todo.list.TodoListRepository
import com.dovydasvenckus.todo.list.TodoListRepositoryImpl
import com.dovydasvenckus.jdbc.mapping.ResultSetToListMapper

import java.sql.ResultSet

class TodoRepositorySpec extends RepositorySpec {
    ResultSetToListMapper<Todo> mapper = new ResultSetToListMapper<>(new TodoMapper())

    TodoListRepository todoListRepository

    TodoRepository todoRepository

    TodoList todoList


    def setup() {
        todoListRepository = new TodoListRepositoryImpl(connection)
        todoRepository = new TodoRepositoryImpl(connection)

        todoList = new TodoList("Index")
        todoListRepository.create(todoList)
    }

    def 'should add new todo item'() {
        given:
            Todo todo = new Todo('fake title', 1)

        when:
            todoRepository.add(todo)

        then:
            List<Todo> todoItems = getAllTodoItems()
            todoItems.size() == 1

            with(todoItems.first()) {
                id == 1
                title == 'fake title'
                todoListId == todoList.id
            }
    }

    def 'should be able to add two items to list'() {
        given:
            Todo firstTodo = new Todo('first title', 1)
            Todo secondTodo = new Todo('second title', 1)

        when:
            todoRepository.add(firstTodo)

        and:
            todoRepository.add(secondTodo)

        then:
            List<Todo> todoItems = getAllTodoItems()
            todoItems.size() == 2
    }

    def 'should be able to delete todo'() {
        given:
            Todo todo = new Todo('fake title', 1)
            todoRepository.add(todo)

        when:
            todoRepository.remove(todo.id)

        then:
            List<Todo> todoItems = getAllTodoItems()
            todoItems.size() == 0
    }

    def 'should be able to update todo item'() {
        given:
            Todo todo = new Todo('fake title', 1)
            todoRepository.add(todo)

        when:
            todoListRepository.create(new TodoList('second list'))
            todo.title = 'new title'
            todo.todoListId = 2
            todo.setIsDone(true)
            todoRepository.update(todo)

        then:
            List<Todo> todoItems = getAllTodoItems()
            todoItems.size() == 1

            with(todoItems.first()) {
                title == 'new title'
                todoListId == 2
                isDone
            }
    }

    def 'should be able to find by id'() {
        given:
            todoRepository.add(new Todo('first title', 1))
            todoRepository.add(new Todo('second title', 1))
            todoRepository.add(new Todo('third title', 1))
        when:
            Todo todo = todoRepository.find(2).get()

        then:
            todo.title == 'second title'
    }

    def 'should be able to list all todos'() {
        given:
            todoRepository.add(new Todo('first title', 1))
            todoRepository.add(new Todo('second title', 1))
            todoRepository.add(new Todo('third title', 1))

        when:
            List<Todo> allTodoItems = todoRepository.listAll()

        then:
            allTodoItems.size() == 3
    }

    def 'should list only done items'() {
        given:
            Todo firstDoneItem = new Todo('first title', 1)
            firstDoneItem.isDone = true
            Todo secondDoneItem = new Todo('second title', 1)
            secondDoneItem.isDone = true

            todoRepository.add(firstDoneItem)
            todoRepository.add(new Todo('second title', 1))
            todoRepository.add(secondDoneItem)

        when:
            List<Todo> allTodoItems = todoRepository.listDone()

        then:
            allTodoItems.size() == 2
    }

    def 'should list only active items'() {
        given:
            Todo doneItem = new Todo('first title', 1)
            doneItem.isDone = true

            todoRepository.add(doneItem)
            todoRepository.add(new Todo('second title', 1))
            todoRepository.add(new Todo('other title', 1))

        when:
            List<Todo> allTodoItems = todoRepository.listActive()

        then:
            allTodoItems.size() == 2
    }

    def 'should be able to list todo items for specified list'() {
        given:
            todoRepository.add(new Todo('first lists item', todoList.id))

        and:
            TodoList secondTodoList = new TodoList('second')
            todoListRepository.create(secondTodoList)

            todoRepository.add(new Todo('second list item', secondTodoList.id))
            todoRepository.add(new Todo('second list item', secondTodoList.id))

        when:
            List<Todo> firstList = todoRepository.list(todoList)
            List<Todo> secondList = todoRepository.list(secondTodoList)

        then:
            firstList.size() == 1
            firstList.first().title == 'first lists item'

        and:
            secondList.size() == 2
            secondList.forEach {
                it.title == 'second list item'
            }
    }

    private List<Todo> getAllTodoItems() {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM todo")
        return mapper.getListOfResults(resultSet)
    }

}
