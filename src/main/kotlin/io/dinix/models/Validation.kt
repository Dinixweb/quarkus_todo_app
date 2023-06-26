package io.dinix.models

import io.dinix.resource.NormalTodo
import io.dinix.resource.TodoResource
import jakarta.ws.rs.core.Response
import org.slf4j.LoggerFactory

object Validation {
    private val logger = LoggerFactory.getLogger(Validation::class.java)
    private const val TitleLength = 40
    private const val DescLength = 100

    private fun basicStringValidation(str :String?, length : Int) : Boolean {
        return if (str == null){
            logger.debug("string is null")
            false
        }
        else{
            if(str.length in 1..length) {
                logger.debug("string: $str is valid")
                true
            }
            else{
                logger.debug("string: $str has invalid length")
                false
            }
        }
    }

    fun isValidTitle(str : String? ) = basicStringValidation(str, TitleLength)
    fun isValidDescription(str : String? ) = basicStringValidation(str, DescLength)

    fun validateTodoData(normalTodo: NormalTodo): Boolean {
        val isValidTitle = isValidTitle(normalTodo.title)
        val isValidDesc = isValidDescription(normalTodo.description)
        logger.debug("isValidTitle: $isValidTitle String: ${normalTodo.title}  Description: ${normalTodo.description}" )
        return isValidTitle && isValidDesc
    }
}