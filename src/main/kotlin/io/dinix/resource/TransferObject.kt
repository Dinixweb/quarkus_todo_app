package io.dinix.resource

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonProperty

data class NormalTodo(
    @field:JsonProperty("title")
    val title: String,
    @field:JsonProperty("description")
    val description: String
) {
    @JsonAnySetter
    private fun handleUnknownField(key: String, value: Any) {
        throw UnsupportedOperationException("Unknown field: $key")
    }
}
