package com.example.nurkowapolskaapp.app.functions.maps

import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng

// Camera if permission not granted will start with Warsaw in center
var currentUserLocation = LatLng(51.91890, 19.13437)

var markers = mutableStateListOf<Marker>()
var markerFilter: MarkersType = MarkersType.CRAYFISH

enum class MarkersType {
    CRAYFISH, DANGER, POLLUTION
}

enum class CrayfishesType {
    NOBLE, AMERICAN, SIGNAL, MUD, UNVERIFIED, NONE
}

data class Marker(
    val lat: Double,
    val lng: Double,
    val title: String,
    val description: String,
    val date: String,
    val markerType: MarkersType,
    val crayfishType: CrayfishesType,
)