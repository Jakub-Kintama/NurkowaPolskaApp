package com.example.nurkowapolskaapp.app.functions.map

import android.media.Image
import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDate

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
    val image: Image? = null
)

data class Position(
    val lat: Double,
    val lng: Double,
)

data class MapMarker(
    val position: Position,
    val title: String,
    val description: String
)

var markerList = mutableStateListOf<Marker>()

data class Marker(
    val _id: String,
    val mapMarker: MapMarker,
    val userEmail: String,
    val crayfishType: CrayfishType,
    val date: LocalDate,
    val verified: Boolean,
    val image: Image? = null
)

enum class MarkerType {
    CRAYFISH, DANGER, POLLUTION
}

enum class CrayfishType {
    SIGNAL, AMERICAN, NOBLE, GALICIAN, NONE
}