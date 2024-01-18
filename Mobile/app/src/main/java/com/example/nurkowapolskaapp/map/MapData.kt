package com.example.nurkowapolskaapp.map

import androidx.compose.runtime.mutableStateListOf
import com.example.nurkowapolskaapp.map.model.Marker
import com.google.android.gms.maps.model.LatLng

// Camera if permission not granted will start with Warsaw in center
var currentUserLocation = LatLng(52.00, 19.00)

var markerList = mutableStateListOf<Marker>()