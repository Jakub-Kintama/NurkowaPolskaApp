package com.example.nurkowapolskaapp.map.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Image(
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("data")
    val data: String? = null,
)