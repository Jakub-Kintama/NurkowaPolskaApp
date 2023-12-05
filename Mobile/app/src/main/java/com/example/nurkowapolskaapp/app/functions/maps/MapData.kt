package com.example.nurkowapolskaapp.app.functions.maps

import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng

// Camera if permission not granted will start with Warsaw in center
var currentUserLocation = LatLng(51.91890, 19.13437)

var markerMockList = mutableStateListOf<MarkerMock>()
var markerMockFilter: MarkerMockType = MarkerMockType.CRAYFISH

enum class MarkerMockType {
    CRAYFISH, DANGER, POLLUTION
}

enum class CrayfishMockType {
    NOBLE, AMERICAN, SIGNAL, MUD, UNVERIFIED, NONE
}

data class MarkerMock(
    val lat: Double,
    val lng: Double,
    val title: String,
    val description: String,
    val date: String,
    val markerType: MarkerMockType,
    val crayfishType: CrayfishMockType,
)