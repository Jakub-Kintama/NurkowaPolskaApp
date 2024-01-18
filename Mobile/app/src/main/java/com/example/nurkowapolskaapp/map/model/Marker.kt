package com.example.nurkowapolskaapp.map.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDate

typealias markerImage = Image

@JsonPropertyOrder(value = ["_id", "mapMarker", "userEmail", "CrayfishType", "date", "verified", "image"])
data class Marker(
    @JsonProperty("_id")
    val id: String? = null,
    @JsonProperty("mapMarker")
    val mapMarker: MapMarker,
    @JsonProperty("userEmail")
    val userEmail: String,
    @JsonProperty("CrayfishType")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    val CrayfishType: CrayfishType,
    @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    val date: LocalDate,
    @JsonProperty("verified")
    val verified: Boolean,
    @JsonProperty("image")
    val image: markerImage? = null
)