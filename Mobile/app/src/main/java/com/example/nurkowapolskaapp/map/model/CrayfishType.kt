package com.example.nurkowapolskaapp.map.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class CrayfishType {
    @JsonProperty("SIGNAL")
    SIGNAL,
    @JsonProperty("AMERICAN")
    AMERICAN,
    @JsonProperty("NOBLE")
    NOBLE,
    @JsonProperty("GALICIAN")
    GALICIAN,
    @JsonProperty("OTHER")
    OTHER
}