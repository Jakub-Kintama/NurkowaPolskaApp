package com.example.nurkowapolskaapp.app.functions.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.nurkowapolskaapp.R
import com.google.android.gms.location.LocationServices
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
    Map()
}

@Composable
fun Map() {
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

    val showMarker = remember { mutableStateOf(MarkersType.CRAYFISH) }
    val showFormWindow = remember { mutableStateOf(false) }

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
                for(mapMarker in markers) {
                    val position = LatLng(mapMarker.lat, mapMarker.lng)
                    mapMarker.date
                    mapMarker.title
                    if(showMarker.value == mapMarker.markerType) {
                        val openExtendedDialog = remember { mutableStateOf(false) }
                        MarkerInfoWindow(
                            state = MarkerState(position = position),
                            icon =
                            when(mapMarker.markerType.toString()) {
                                "CRAYFISH" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                                "DANGER" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                                "POLLUTION" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                                else -> { BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA) }
                            },
                            onInfoWindowClick = { openExtendedDialog.value = true }
                        ) {
                            CustomMarkerInfoWindow(mapMarker)
                            ExtendedInfoWindow(openExtendedDialog, mapMarker)
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomStart)) {
            FilterButton(showMarker)
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            AddMarker(showFormWindow, permissionCheck, context)
        }
    }

}

@Composable
fun CustomMarkerInfoWindow(marker: Marker) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
            .width(250.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageModifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
            when(marker.markerType) {
                MarkersType.CRAYFISH -> Image(
                    painter = painterResource(id = R.drawable.crayfish),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier
                )
                MarkersType.DANGER -> Image(
                    painter = painterResource(id = R.drawable.warning),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier
                )
                MarkersType.POLLUTION -> Image(
                    painter = painterResource(id = R.drawable.hazard),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${marker.title} (${marker.date})",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Kliknij okno żeby dostać więcej informacji", fontSize = 13.sp, textAlign = TextAlign.Center)
        }
    }
}

//@Preview
//@Composable
//fun PreviewCustomMarkerInfoWindow() {
//    val previewMarker = Marker(50.00, 20.00, "Rak", "Super opis Raka", "10-11-2023", MarkersType.POLLUTION, CrayfishesType.UNVERIFIED)
//    val openPreviewDialog = remember { mutableStateOf(false) }
//    CustomMarkerInfoWindow(previewMarker, openPreviewDialog)
//}

@Composable
fun ExtendedInfoWindow(
    openAlertDialog: MutableState<Boolean>,
    marker: Marker
) {
    when {
        openAlertDialog.value -> {
            Dialog(onDismissRequest = { openAlertDialog.value = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Box(modifier = Modifier.padding(14.dp)) {
                        Column {
                            Image(
                                painterResource(R.drawable.noble_zdenek_macat_shutterstock_example),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                Text(
                                    text = "Znacznik: ",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Left,
                                )
                                Text(
                                    text = "${marker.markerType}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Left
                                )
                            }
                            if(marker.markerType == MarkersType.CRAYFISH) {
                                Spacer(modifier = Modifier.height(5.dp))
                                Row {
                                    Text(
                                        text = " Typ raka: ",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Left,
                                    )
                                    Text(
                                        text = "${marker.crayfishType}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Left
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "OPIS",
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = marker.description,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewExtendedInfoWindow() {
    val previewMarker = Marker(50.00, 20.00, "Rak", "Super opis Raka", "10-11-2023", MarkersType.CRAYFISH, CrayfishesType.MUD)
    val openPreviewDialog = remember { mutableStateOf(true) }
    ExtendedInfoWindow(openPreviewDialog, previewMarker)
}

@Composable
fun FilterButton(
    showMarker: MutableState<MarkersType>,

) {
    val filterNames = arrayOf("Raki", "Zagrożenia", "Zanieczyszczenia")
    Box {
        Column(modifier = Modifier.padding(15.dp)) {
            ExtendedFloatingActionButton(
                modifier = Modifier.height(40.dp),
                onClick = { showMarker.value = MarkersType.CRAYFISH },
                icon = { Icon(Icons.Filled.Settings, "Filtr znaczników: raki") },
                text = { Text(text = filterNames[0]) },
            )
            Spacer(modifier = Modifier.height(10.dp))
            ExtendedFloatingActionButton(
                modifier = Modifier.height(40.dp),
                onClick = { showMarker.value = MarkersType.DANGER },
                icon = { Icon(Icons.Filled.Settings, "Filtr znaczników: zagrożenia") },
                text = { Text(text = filterNames[1]) },
            )
            Spacer(modifier = Modifier.height(10.dp))
            ExtendedFloatingActionButton(
                modifier = Modifier.height(40.dp),
                onClick = { showMarker.value = MarkersType.POLLUTION },
                icon = { Icon(Icons.Filled.Settings, "Filtr znaczników: zanieczyszczenia") },
                text = { Text(text = filterNames[2]) },
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMarker(
    openFormWindow: MutableState<Boolean>,
    permissionCheck: ManagedActivityResultLauncher<String, Boolean>,
    context: Context
) {
    FloatingActionButton(
        modifier = Modifier.padding(15.dp),
        onClick = {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    openFormWindow.value = true
                } else -> {
                permissionCheck.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }
    ) {
        Icon(Icons.Filled.Add, contentDescription = null)
    }

    when {
        openFormWindow.value -> {

            Dialog(onDismissRequest = { openFormWindow.value = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                ) {
                    Box(
                        modifier = Modifier.padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        var expandedMarkerType by remember { mutableStateOf(false) }
                        val markerTypes = arrayOf(MarkersType.CRAYFISH, MarkersType.DANGER, MarkersType.POLLUTION)
                        var selectedMarkerType by remember { mutableStateOf(markerTypes[0]) }
                        val markerTypesString = arrayOf("Rak", "Zagrożenie", "Zanieczyszczenie")
                        var selectedMarkerTypeString by remember { mutableStateOf(markerTypesString[0]) }

                        Column {
                            Text("Typ znacznika:")
                            ExposedDropdownMenuBox(
                                expanded = expandedMarkerType,
                                onExpandedChange = { expandedMarkerType = !expandedMarkerType }
                            ) {
                                TextField(
                                    value = selectedMarkerTypeString,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedMarkerType
                                        )
                                    },
                                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    modifier = Modifier.menuAnchor(),
                                )

                                ExposedDropdownMenu(
                                    expanded = expandedMarkerType,
                                    onDismissRequest = { expandedMarkerType = false }) {
                                    DropdownMenuItem(
                                        text = { Text(text = "Rak") },
                                        onClick = {
                                            selectedMarkerType = markerTypes[0]
                                            selectedMarkerTypeString = markerTypesString[0]
                                            expandedMarkerType = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = "Niebezpieczeństwo") },
                                        onClick = {
                                            selectedMarkerType = markerTypes[1]
                                            selectedMarkerTypeString = markerTypesString[1]
                                            expandedMarkerType = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = "Zanieczyszczenie") },
                                        onClick = {
                                            selectedMarkerType = markerTypes[2]
                                            selectedMarkerTypeString = markerTypesString[2]
                                            expandedMarkerType = false
                                        }
                                    )
                                }
                            }

                            var expandedCrayfishType by remember { mutableStateOf(false) }
                            val crayfishTypes = arrayOf(CrayfishesType.NOBLE, CrayfishesType.AMERICAN, CrayfishesType.SIGNAL, CrayfishesType.MUD, CrayfishesType.UNVERIFIED, CrayfishesType.NONE)
                            var selectedCrayfishType by remember { mutableStateOf(crayfishTypes[5]) }
                            val crayfishTypesString = arrayOf("Szlachetny", "Amerykański", "Sygnałowy", "Błotny", "Niezweryfikowany")
                            var selectedCrayfishTypeString by remember { mutableStateOf(crayfishTypesString[0]) }

                            if (selectedMarkerType == MarkersType.CRAYFISH) {
                                Spacer(modifier = Modifier.height(25.dp))
                                Column {


                                    Text("Typ raka:")
                                    ExposedDropdownMenuBox(
                                        expanded = expandedCrayfishType,
                                        onExpandedChange = { expandedCrayfishType = !expandedCrayfishType }
                                    ) {
                                        TextField(
                                            value = selectedCrayfishTypeString,
                                            onValueChange = {},
                                            readOnly = true,
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                    expanded = expandedCrayfishType
                                                )
                                            },
                                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                            modifier = Modifier.menuAnchor(),
                                        )

                                        ExposedDropdownMenu(
                                            expanded = expandedCrayfishType,
                                            onDismissRequest = { expandedCrayfishType = false }) {
                                            DropdownMenuItem(
                                                text = { Text(text = "Szlachetny") },
                                                onClick = {
                                                    selectedCrayfishType = crayfishTypes[0]
                                                    selectedCrayfishTypeString = crayfishTypesString[0]
                                                    expandedCrayfishType = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text(text = "Amerykański") },
                                                onClick = {
                                                    selectedCrayfishType = crayfishTypes[1]
                                                    selectedCrayfishTypeString = crayfishTypesString[1]
                                                    expandedCrayfishType = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text(text = "Sygnałowy") },
                                                onClick = {
                                                    selectedCrayfishType = crayfishTypes[2]
                                                    selectedCrayfishTypeString = crayfishTypesString[2]
                                                    expandedCrayfishType = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text(text = "Błotny") },
                                                onClick = {
                                                    selectedCrayfishType = crayfishTypes[3]
                                                    selectedCrayfishTypeString = crayfishTypesString[3]
                                                    expandedCrayfishType = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text(text = "Niezweryfikowany") },
                                                onClick = {
                                                    selectedCrayfishType = crayfishTypes[4]
                                                    selectedCrayfishTypeString = crayfishTypesString[4]
                                                    expandedCrayfishType = false
                                                }
                                            )

                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            var title by remember { mutableStateOf("") }
                            OutlinedTextField(value = title, onValueChange = { title = it}, label = { Text("Nazwa lokacji znacznika") })
                            Spacer(modifier = Modifier.height(12.dp))
                            var description by remember { mutableStateOf("") }
                            OutlinedTextField(value = description, onValueChange = { description = it}, label = { Text("Opis znacznika") })

                            Spacer(modifier = Modifier.height(12.dp))
                            Button(onClick = {
                                when(PackageManager.PERMISSION_GRANTED) {
                                    ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                    ) -> {
                                        val userLocation = LocationServices.getFusedLocationProviderClient(context)
                                        userLocation.lastLocation.addOnSuccessListener {
                                            location: Location? ->
                                            if(location != null) {
                                                val lat = location.latitude
                                                val lng = location.longitude
                                                val currentDate = getCurrentDate()
                                                markers.add(Marker(lat, lng, title, description, currentDate, selectedMarkerType, selectedCrayfishType))
                                                openFormWindow.value = false
                                            }
                                        }
                                    }
                                }
                            }) {
                                Text(text = "Dodaj znacznik")
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun TestAddMarker() {
//    val openFormWindow = remember { mutableStateOf(true) }
//    AddMarker(openFormWindow)
//}