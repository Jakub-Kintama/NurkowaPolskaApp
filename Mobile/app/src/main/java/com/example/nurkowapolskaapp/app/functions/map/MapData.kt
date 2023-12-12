package com.example.nurkowapolskaapp.app.functions.map

import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng

// Camera if permission not granted will start with Warsaw in center
var currentUserLocation = LatLng(52.22977, 21.01178)

var markerMockList = mutableStateListOf<MarkerMock>()

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