package com.example.nurkowapolskaapp.map.model

import com.fasterxml.jackson.annotation.JsonProperty

typealias customLatLng = LatLng

data class MapMarker(
    @JsonProperty("position")
    val position: customLatLng,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String
)