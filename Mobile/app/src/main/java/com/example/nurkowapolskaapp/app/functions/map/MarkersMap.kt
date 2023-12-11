package com.example.nurkowapolskaapp.app.functions.map

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.nurkowapolskaapp.app.functions.map.buttons.AddMarker
import com.example.nurkowapolskaapp.app.functions.map.buttons.FilterButton
import com.example.nurkowapolskaapp.app.functions.map.marker.CustomMarkerInfoWindow
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MarkersMap() {
    val permissionCheck = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if(isGranted) {
            Log.d("LocationFine","PERMISSION GRANTED")
        } else {
            Log.d("LocationFine","PERMISSION DENIED")
        }
    }

    val context = LocalContext.current

    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) -> {
            permissionCheck.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val filterList = listOf("Crayfish", "DangPoll", "Both")
    val checkedFilter = remember { mutableStateOf(filterList[0]) }
    val showMarker = remember { mutableStateOf(MarkerMockType.CRAYFISH) }
    val showFormWindow = remember { mutableStateOf(false) }

    val showAdditionalInfo = remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentUserLocation, 1f)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = true,
                    mapToolbarEnabled = false
                )
            ) {
                for(mapMarker in markerMockList) {
                    val position = LatLng(mapMarker.lat, mapMarker.lng)
                    mapMarker.date
                    mapMarker.title
                    if(checkedFilter.value == "Crayfish" || checkedFilter.value == "Both") {
                        if(mapMarker.markerType == MarkerMockType.CRAYFISH) {
                            MarkerInfoWindow(
                                state = MarkerState(position = position),
                                icon = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)),
                                onInfoWindowClick = { showAdditionalInfo.value = true }
                            ) {
                                Box(Modifier.padding(10.dp)) {
                                    CustomMarkerInfoWindow(markerMock = mapMarker)
                                }
                            }
                        }
                    }
                    if(checkedFilter.value == "DangPoll" || checkedFilter.value == "Both") {
                        if(mapMarker.markerType == MarkerMockType.DANGER || mapMarker.markerType == MarkerMockType.POLLUTION) {
                            MarkerInfoWindow(
                                state = MarkerState(position = position),
                                icon = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)),
                                onInfoWindowClick = { showAdditionalInfo.value = true }
                            ) {
                                Box(Modifier.padding(10.dp)) {
                                    CustomMarkerInfoWindow(markerMock = mapMarker)
                                }
                            }
                        }
                    }
                }
            }
        }

        val selectedCrayfish = remember { mutableStateOf(true) }
        val selectedDangPoll = remember { mutableStateOf(false) }

        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            Column {
                FilterButton(showMarker, filterList, checkedFilter, selectedCrayfish, selectedDangPoll)
                AddMarker(showFormWindow, permissionCheck, context)
            }
        }
    }
}