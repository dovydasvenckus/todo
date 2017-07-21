package todo

import com.dovydasvenckus.todo.list.TodoListService
import com.dovydasvenckus.todo.todo.Todo
import com.dovydasvenckus.todo.todo.TodoRepository
import com.dovydasvenckus.todo.todo.TodoService
import org.sql2o.Sql2o
import spock.lang.Specification
import spock.lang.Unroll

import static com.dovydasvenckus.todo.todo.TodoStateFilter.ALL
import static com.dovydasvenckus.todo.todo.TodoStateFilter.DONE
import static com.dovydasvenckus.todo.todo.TodoStateFilter.NOT_DONE

class TodoServiceSpec extends Specification {
    def sql2o = Stub(Sql2o)
    def todoRepository = Stub(TodoRepository)
    def todoListServiceStub = Stub(TodoListService)
    def todoService = new TodoService(sql2o, todoListServiceStub)

    def setup() {
        todoService.todoRepository = todoRepository
    }

    @Unroll
    def "should return #count of active items"() {
        given:
            todoRepository.listActive() >> activeItems
        when:
            def todos = todoService.getTodos(NOT_DONE)
        then:
            todos.size() == count
        where:
            activeItems         | count
            []                  | 0
            [new Todo('test')]  | 1
    }

    @Unroll
    def "should return #count of done items"() {
        given:
            todoRepository.listDone() >> doneItems
        when:
            def todos = todoService.getTodos(DONE)
        then:
            todos.size() == count
        where:
            doneItems           | count
            []                  | 0
            [new Todo('test')]  | 1
    }

    @Unroll
    def "should return #count of all items"() {
        given:
            todoRepository.listAll() >> allItems
        when:
            def todos = todoService.getTodos(ALL)
        then:
            todos.size() == count
        where:
            allItems            | count
            []                  | 0
            [new Todo('test')]  | 1
    }

    def "null should cause NullPointerException"() {
        when:
            todoService.getTodos(null)
        then:
            thrown(NullPointerException)
    }
}
