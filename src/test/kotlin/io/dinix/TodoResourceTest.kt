package io.dinix

import io.dinix.models.EnumPriority
import io.dinix.models.Status
import io.dinix.models.Todo
import io.dinix.service.TodoService
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@QuarkusTest
class TodoResourceTest {

    /**
     * Create Todo Post Request Test
     * Receives the payload as RequestBody
     * Assert the response matches set statusCode
     */
    @Test
    fun `test createTodo method`(){
        val requestBody = """
        {
            "title": "Create a todo app sample",
            "description": "This is a sample todo app",
            "priority": "MEDIUM"
        }
        """.trimIndent()
        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .`when`()
            .post("/todos/createTodo")
            .then()
            .statusCode(200)
            .body(equalTo("Todo created successfully"))
    }

    /**
     * Assert that the response is 200 and response in json
     */
    @Test
    fun testGetAllTodos(){
        RestAssured.given()
            .`when`().get("/todos")
            .then().statusCode(Response.Status.OK.statusCode)
            .contentType(MediaType.APPLICATION_JSON)
    }

    /**
     * Assert that the response is 200 and response in json
     */
    @Test
    fun getTodoById(){
        val todoId = "14"
        RestAssured.given()
            .pathParam("id", todoId)
            .`when`().get("/todos/{id}")
            .then().statusCode(Response.Status.OK.statusCode)
            .contentType(MediaType.APPLICATION_JSON)
    }

    /**
     * Test update for 200 response
     */
    @Test
    fun testUpdateTodos(){
        val todoId = "5T67"
        val todoUpdate = Todo(
            id = todoId,
            title = "Updated Title",
            description = "Updated description",
            dateCreated = LocalDateTime.now(),
            dateModified = LocalDateTime.now(),
            status = Status.CREATED,
            priority = EnumPriority.HIGH
        )
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(todoUpdate)
            .`when`().put("/todos/updateTodo")
            .then().statusCode(Response.Status.OK.statusCode)
            .contentType(MediaType.APPLICATION_JSON)
            .statusCode(200)
            .body(equalTo("Todo updated successfully"))
    }

    /**
     * Assert that response has 200 and response json
     */
    @Test
    fun deleteTodoById(){
        val todoId = "14"
        RestAssured.given()
            .pathParam("id", todoId)
            .`when`().delete("/todos/delete/{id}")
            .then().statusCode(Response.Status.OK.statusCode)
            .contentType(MediaType.APPLICATION_JSON)
            .statusCode(200)
            .body(equalTo("Todo deleted successfully"))
    }

}