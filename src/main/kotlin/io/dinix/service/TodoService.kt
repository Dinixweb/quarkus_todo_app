package io.dinix.service

import io.dinix.constants.GlobalFunction
import io.dinix.models.EnumPriority
import io.dinix.models.Status
import io.dinix.models.Todo
import io.dinix.resource.TodoResource
import jakarta.enterprise.context.ApplicationScoped
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

@ApplicationScoped
class TodoService {

    /**
     * Hard coded todo list
     */
    private val todos = mutableListOf<Todo>(
        Todo(
            "14",
            "Todo 1",
            "Description 1",
            LocalDateTime.now(),
            LocalDateTime.now(),
            Status.CREATED,
            priority = EnumPriority.HIGH,
        ),
        Todo(
            "31",
            "Todo 2",
            "Description 2",
            LocalDateTime.now(),
            LocalDateTime.now(),
            Status.INPROGRESS,
            priority = EnumPriority.HIGH,
        ),
        Todo(
            "89",
            "Todo 3",
            "Description 3",
            LocalDateTime.now(),
            LocalDateTime.now(),
            Status.DONE,
            priority = EnumPriority.LOW,
        )
    );

    /**
     * UserId fun
     */
    private val generated = GlobalFunction()

    companion object {
        private val logger = LoggerFactory.getLogger(TodoResource::class.java)
    }

    /**
     * Creates a todo object
     */
    fun addTodo(priority: EnumPriority, title: String, description: String): Todo {
        val currentDate = LocalDateTime.now()
        val todoId = generated.generatedUserId()
        val createdTodo = Todo(
            id = todoId,
            title = title,
            description = description,
            dateModified = currentDate,
            priority = priority
        )
        todos.add(createdTodo)
        return createdTodo;
    }

    fun createNormalTodo(title: String, description: String): Todo =
        addTodo(EnumPriority.NORMAL, title, description)

    /**
     * Return a list of all todos
     * returns ArrayList<Todo>
     */
    fun getAllTodo(): List<Todo> {
        return todos.toList();
    }

    /**
     * Get a single object of todo
     * returns Todo
     */
    fun getTodoById(id: String): Todo? = todos.find { it.id == id }

    fun updateTodoStatus(todoId: String, status: Status) : Todo?{
        //add Logs
        val todo: Todo?  = getTodoById(todoId)
        return todo?.let {
            val updatedTodo: Todo = modifyStatus(it,status)
            val index = todos.indexOf(todo)
            todos[index] = updatedTodo
            updatedTodo
        }
    }

    /**
     * Change todo status here
     */
    fun modifyStatus(todo: Todo, status: Status):Todo{
        val currentDate = LocalDateTime.now()
        logger.debug("todo:${todo} - status: $status")
        return todo.copy(
            dateModified = currentDate,
            status = status,
        )
    }
}