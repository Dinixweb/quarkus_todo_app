package io.dinix.resource

import io.dinix.models.Status
import io.dinix.models.Todo
import io.dinix.models.Validation
import io.dinix.service.TodoService
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.jboss.resteasy.reactive.PartType
import org.slf4j.LoggerFactory
import javax.validation.Valid

@Path("/todos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class TodoResource @Inject constructor(private val todoService: TodoService) {

    companion object {
        private val logger = LoggerFactory.getLogger(TodoResource::class.java)
    }

    /**
     * Get Request: Fetch all todo items available
     */
    @GET
    fun getAllTodos(): List<Todo> {
        return todoService.getAllTodo()
    }

    /**
     * Get Request: Fetch a single todo item by Id
     */
    @GET
    @Path("/{id}")
    fun getTodoById(@PathParam("id") id: String): Todo? {
        return todoService.getTodoById(id)
    }

    /**
     * Post Request: Handles creation of new todo item
     */
    @POST
    @Path("/todo")
    @Consumes(MediaType.APPLICATION_JSON)
    fun createNormalTodo(normalTodo: NormalTodo): Response {
        return try {
            logger.debug("[createNormalTodo] title: ${normalTodo.title}, desc : ${normalTodo.description}")
            val isValidTodo = Validation.validateTodoData(normalTodo)
            if (isValidTodo){
                val createdTodo = todoService.createNormalTodo(normalTodo.title, normalTodo.description)
                Response.ok(createdTodo).entity("Todo created successfully")
                    .type(MediaType.APPLICATION_JSON)
                    .build()
            }else{
                Response.status(Response.Status.BAD_REQUEST).entity("title length not more than 20 and description 100").build()
            }
        }catch(e:UnsupportedOperationException){
            Response.status(Response.Status.BAD_REQUEST).entity("unsupported payload format").build()
        }

    }

    /**
     * Utility function for modification operations
     */
    fun todoModification(todoId: String, status: Status): Response {
        logger.debug("todoId: $todoId - status: $status")
        val updatedTodo = todoService.updateTodoStatus(todoId, status)
        return if (updatedTodo != null) {
            Response.ok(updatedTodo).build()
        } else {
            Response.status(Response.Status.NOT_FOUND).entity("No ID found").build()
        }
    }

    /**
     * Put Request: change todo status to done
     */
    @PUT
    @Path("/statusToDone/{todoId}")
    fun doneTodoItem(@PathParam("todoId") todoId: String): Response {
        logger.debug("todoId: $todoId")
        return todoModification(todoId, Status.DONE)
    }

    /**
     * Put Request: change todo status to in-progress
     */
    @PUT
    @Path("/statusToInprogress/{todoId}")
    fun inProgressTodoItem(@PathParam("todoId") todoId: String): Response {
        logger.debug("todoId: $todoId")
        return todoModification(todoId, Status.INPROGRESS)
    }

    /**
     * Put Request: change todo status to in-progress
     */
    @PUT
    @Path("/statusToDelete/{todoId}")
    fun deleteTodoItem(@PathParam("todoId") todoId: String): Response {
        logger.debug("todoId: $todoId")
        return todoModification(todoId, Status.DELETED)
    }


    /**
     * 1 remove the unused data class
     * write valdiation for description, reuse function of title but with  different string length
     * validate if json have title and description only
     * after validation
     * create a normal todo
     */



}