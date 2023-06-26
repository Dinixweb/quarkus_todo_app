package io.dinix.models
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime


data class Todo(

    val id: String,
    val title: String,
    val description: String,
    val dateCreated: LocalDateTime = LocalDateTime.now(),
    val dateModified: LocalDateTime,
    val status: Status = Status.CREATED,
    val priority: EnumPriority = EnumPriority.NORMAL
)