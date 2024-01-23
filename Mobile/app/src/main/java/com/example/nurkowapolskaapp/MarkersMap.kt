package com.example.nurkowapolskaapp

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.nurkowapolskaapp.api.ViewModelApi
import com.example.nurkowapolskaapp.functions.isDateInRange
import com.example.nurkowapolskaapp.map.CustomMarkerInfoWindow
import com.example.nurkowapolskaapp.map.buttons.AddMarker
import com.example.nurkowapolskaapp.map.buttons.FilterButton
import com.example.nurkowapolskaapp.map.currentUserLocation
import com.example.nurkowapolskaapp.map.model.CrayfishType
import com.example.nurkowapolskaapp.map.model.DateRange
import com.example.nurkowapolskaapp.map.model.Marker
import com.example.nurkowapolskaapp.map.model.MarkerFilterOptions
import com.example.nurkowapolskaapp.signin.ViewModelAuth
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MarkersMap(
    viewModelApi: ViewModelApi,
    viewModelAuth: ViewModelAuth,
    isUserSignedIn: Boolean
) {
    val context = LocalContext.current

    val myLocationEnabled = remember {mutableStateOf(false)}

    val filterOptions = remember {mutableStateOf(MarkerFilterOptions(dateRange = DateRange()))}

    val permissionCheck  = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if(isGranted) {
            myLocationEnabled.value = true
            Log.d("LocationCoarse","PERMISSION GRANTED")
            Log.d("LocationFine","PERMISSION GRANTED")
        } else {
            Log.d("LocationCoarse","PERMISSION DENIED")
            Log.d("LocationFine","PERMISSION DENIED")
        }
    }

    DisposableEffect(context) {
        permissionCheck.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        onDispose {
        }
    }

    LaunchedEffect(Unit) {
        viewModelApi.getMarkers()
    }

    val uiSettings = remember {
        MapUiSettings(
            myLocationButtonEnabled = myLocationEnabled.value,
            zoomControlsEnabled = false,
            mapToolbarEnabled = false
        )
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = myLocationEnabled.value))
    }

    val showFormWindow = remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentUserLocation, 5.5f)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = uiSettings,
                properties = properties
            ) {
                val filterDateStart = filterOptions.value.dateRange.dateBegin
                val filterDateEnd = filterOptions.value.dateRange.dateEnd
                for(marker: Marker in viewModelApi.markerList.value) {
                    Log.d("DATE", "${marker.date}")
                    val position = marker.mapMarker.position
                    var lat: Double
                    var lng: Double
                    val latString = String.format("%.7f", position.lat)
                    val lngString = String.format("%.7f", position.lng)
                    lat = latString.toDouble()
                    lng = lngString.toDouble()
                    Log.d("position", position.toString())
                    if(filterOptions.value.showCrayfish && isDateInRange(marker.date, filterOptions.value.dateRange.dateBegin, filterOptions.value.dateRange.dateEnd
                    )) {
                        if (marker.CrayfishType != CrayfishType.OTHER && marker.verified) {
                            MarkerInfoWindow(
                                state = MarkerState(
                                    position = LatLng(
                                        lat,
                                        lng
                                    )
                                ),
                                icon = (BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_GREEN
                                )),
                                onInfoWindowClick = { }
                            ) {
                                Box(Modifier.padding(10.dp)) {
                                    CustomMarkerInfoWindow(
                                        marker = marker.mapMarker,
                                        markerDate = marker.date,
                                        crayfishType = marker.CrayfishType,
                                        image = marker.image
                                    )
                                }
                            }
                        }
                        if (marker.CrayfishType != CrayfishType.OTHER && !marker.verified && filterOptions.value.showUnverified) {
                            MarkerInfoWindow(
                                state = MarkerState(
                                    position = LatLng(
                                        lat,
                                        lng
                                    )
                                ),
                                icon = (BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_ROSE
                                )),
                                onInfoWindowClick = { }
                            ) {
                                Box(Modifier.padding(10.dp)) {
                                    CustomMarkerInfoWindow(
                                        marker = marker.mapMarker,
                                        markerDate = marker.date,
                                        crayfishType = marker.CrayfishType,
                                        image = marker.image
                                    )
                                }
                            }
                        }
                    }
                    if(filterOptions.value.showOther && isDateInRange(marker.date, filterDateStart, filterDateEnd)) {
                        if(marker.CrayfishType == CrayfishType.OTHER && marker.verified) {
                            MarkerInfoWindow(
                                state = MarkerState(position = LatLng(lat, lng)),
                                icon = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)),
                                onInfoWindowClick = {  }
                            ) {
                                Box(Modifier.padding(10.dp)) {
                                    CustomMarkerInfoWindow(
                                        marker = marker.mapMarker,
                                        markerDate = marker.date,
                                        crayfishType = marker.CrayfishType,
                                        image = marker.image
                                    )
                                }
                            }
                        }
                        if(marker.CrayfishType == CrayfishType.OTHER && !marker.verified && filterOptions.value.showUnverified) {
                            MarkerInfoWindow(
                                state = MarkerState(position = LatLng(lat, lng)),
                                icon = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)),
                                onInfoWindowClick = {  }
                            ) {
                                Box(Modifier.padding(10.dp)) {
                                    CustomMarkerInfoWindow(
                                        marker = marker.mapMarker,
                                        markerDate = marker.date,
                                        crayfishType = marker.CrayfishType,
                                        image = marker.image
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            Column {
                if(isUserSignedIn) {
                    AddMarker(showFormWindow, permissionCheck, context, viewModelApi, viewModelAuth)
                }
                FilterButton(filterOptions)
            }
        }
    }
}