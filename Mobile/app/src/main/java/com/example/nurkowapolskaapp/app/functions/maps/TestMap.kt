package com.example.nurkowapolskaapp.app.functions.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nurkowapolskaapp.R
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TestMap(navController: NavController) {
    // Mock data
    markerMockList.plus(MarkerMock(52.22977, 21.01178, "Warszawa", "Miasto Warszawa", "14-10-2023", MarkerMockType.CRAYFISH, CrayfishMockType.MUD))
    markerMockList.plus(MarkerMock(54.35205, 18.64637, "Gdańsk", "Miasto Gdańsk", "14-10-2023", MarkerMockType.DANGER, CrayfishMockType.NONE))

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentUserLocation, 5.5f)
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Box {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = true),
                googleMapOptionsFactory = { GoogleMapOptions().mapId((R.string.map_id).toString()) }
            ) {
                for (marker in markerMockList) {
                    val mPosition = LatLng(marker.lat, marker.lng)
                    val mType = marker.title
                    val mSnippet = marker.markerType.toString() +" "+ marker.description
                    if(markerMockFilter == marker.markerType) {
                        AdvancedMarker(
                            state = MarkerState(position = mPosition),
                            title = mType,
                            snippet = mSnippet,
                            visible = true,
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp)
        ) {
            AddMarkerButton(navController)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        ) {
            FilterMarkers(navController)
        }
    }
}

@Composable
fun FilterMarkers(
    navController: NavController,
) {
    Column(modifier = Modifier.height(IntrinsicSize.Max)) {
        SmallFloatingActionButton(onClick = {
            markerMockFilter = MarkerMockType.CRAYFISH
            navController.popBackStack()
            navController.navigate("mapOfMarkers")
        }) {
            Row(modifier = Modifier.padding(5.dp)) {
                Icon(Icons.Filled.Place, null)
                Text(text = "Raki")
            }
        }
        SmallFloatingActionButton(onClick = {
            markerMockFilter = MarkerMockType.DANGER
            navController.popBackStack()
            navController.navigate("mapOfMarkers")
        }) {
            Row(modifier = Modifier.padding(5.dp)) {
                Icon(Icons.Filled.Clear, null)
                Text(text = "Zagrożenia")
            }
        }
        SmallFloatingActionButton(onClick = {
            markerMockFilter = MarkerMockType.POLLUTION
            navController.popBackStack()
            navController.navigate("mapOfMarkers")
        }) {
            Row(modifier = Modifier.padding(5.dp)) {
                Icon(Icons.Filled.Delete
                    , null)
                Text(text = "Zanieczyszczenia")
            }
        }
    }
}