package com.example.nurkowapolskaapp.map.model

import com.fasterxml.jackson.annotation.JsonProperty

data class LatLng(
    @JsonProperty("lat")
    val lat: Double,
    @JsonProperty("lng")
    val lng: Double
)