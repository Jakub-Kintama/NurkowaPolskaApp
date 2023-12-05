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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
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

    val filterList = listOf("Crayfish", "DangPoll", "Both")
    val checkedFilter = remember { mutableStateOf(filterList[0]) }
    val showMarker = remember { mutableStateOf(MarkerMockType.CRAYFISH) }
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
                for(mapMarker in markerMockList) {
                    val position = LatLng(mapMarker.lat, mapMarker.lng)
                    mapMarker.date
                    mapMarker.title
                    if(checkedFilter.value == "Crayfish" || checkedFilter.value == "Both") {
                        if(mapMarker.markerType == MarkerMockType.CRAYFISH) {
                            val openExtendedDialog = remember { mutableStateOf(false) }
                            MarkerInfoWindow(
                                state = MarkerState(position = position),
                                icon = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)),
                                onInfoWindowClick = { openExtendedDialog.value = true }
                            ) {
                                CustomMarkerInfoWindow(mapMarker)
                                ExtendedInfoWindow(openExtendedDialog, mapMarker)
                            }
                        }
                    }
                    if(checkedFilter.value == "DangPoll" || checkedFilter.value == "Both") {
                        if(mapMarker.markerType == MarkerMockType.DANGER || mapMarker.markerType == MarkerMockType.POLLUTION) {
                            val openExtendedDialog = remember { mutableStateOf(false) }
                            MarkerInfoWindow(
                                state = MarkerState(position = position),
                                icon = (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)),
                                onInfoWindowClick = { openExtendedDialog.value = true }
                            ) {
                                CustomMarkerInfoWindow(mapMarker)
                                ExtendedInfoWindow(openExtendedDialog, mapMarker)
                            }
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            Column {
                FilterButton(showMarker, filterList, checkedFilter)
                AddMarker(showFormWindow, permissionCheck, context)
            }
        }
    }

}

@Composable
fun CustomMarkerInfoWindow(markerMock: MarkerMock) {
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
            when(markerMock.markerType) {
                MarkerMockType.CRAYFISH -> Image(
                    painter = painterResource(id = R.drawable.placeholder_crayfish),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier
                )
                MarkerMockType.DANGER -> Image(
                    painter = painterResource(id = R.drawable.placeholder_danger),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier
                )
                MarkerMockType.POLLUTION -> Image(
                    painter = painterResource(id = R.drawable.placeholder_pollution),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${markerMock.title} (${markerMock.date})",
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

@Composable
fun ExtendedInfoWindow(
    openAlertDialog: MutableState<Boolean>,
    markerMock: MarkerMock
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
                                painterResource(R.drawable.placeholder_image),
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
                                    text = "${markerMock.markerType}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Left
                                )
                            }
                            if(markerMock.markerType == MarkerMockType.CRAYFISH) {
                                Spacer(modifier = Modifier.height(5.dp))
                                Row {
                                    Text(
                                        text = " Typ raka: ",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Left,
                                    )
                                    Text(
                                        text = "${markerMock.crayfishType}",
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
                            Text(text = markerMock.description,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterButton(
    showMarker: MutableState<MarkerMockType>,
    filterType: List<String>,
    checkedFilter: MutableState<String>,
    ) {
    val openFilterBottomSheet = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    FloatingActionButton(
        onClick = { openFilterBottomSheet.value = true },
        modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 15.dp, end = 15.dp),
    ) {
        Icon(Icons.Filled.Search, "Filtr znaczników: raki")
    }
    when {
        openFilterBottomSheet.value -> {
            ModalBottomSheet(
                onDismissRequest = { openFilterBottomSheet.value = false },
                sheetState = sheetState
            ) {
                var selectedCrayfish by remember { mutableStateOf(true) }
                var selectedDangPoll by remember { mutableStateOf(false) }
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    FilterChip(
                        modifier = Modifier.padding(15.dp),
                        selected = selectedCrayfish,
                        onClick = { selectedCrayfish = !selectedCrayfish },
                        label = { Text("Raki") },
                        leadingIcon = if (selectedCrayfish) {
                            {
                                checkedFilter.value = filterType[0]
                                showMarker.value = MarkerMockType.CRAYFISH
                                Icon(
                                    Icons.Filled.Done,
                                    null,
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                    FilterChip(
                        modifier = Modifier.padding(15.dp),
                        selected = selectedDangPoll,
                        onClick = { selectedDangPoll = !selectedDangPoll },
                        label = { Text("Zagrożenia/Zanieczyszczenia") },
                        leadingIcon = if (selectedDangPoll) {
                            {
                                checkedFilter.value = filterType[1]
                                showMarker.value = MarkerMockType.POLLUTION
                                Icon(
                                    Icons.Filled.Done,
                                    null,
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )
                    if (selectedCrayfish && selectedDangPoll) {
                        checkedFilter.value = filterType[2]
                    }
                }
            }
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
        modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 15.dp, end = 15.dp),
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

    val sheetState = rememberModalBottomSheetState()
    when {
        openFormWindow.value -> {

            ModalBottomSheet(
                onDismissRequest = { openFormWindow.value = false },
                sheetState = sheetState
            ) {
                Box(
                    modifier = Modifier.padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    var expandedMarkerType by remember { mutableStateOf(false) }
                    val markerTypes = arrayOf(MarkerMockType.CRAYFISH, MarkerMockType.DANGER, MarkerMockType.POLLUTION)
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
                        val crayfishTypes = arrayOf(CrayfishMockType.NOBLE, CrayfishMockType.AMERICAN, CrayfishMockType.SIGNAL, CrayfishMockType.MUD, CrayfishMockType.UNVERIFIED, CrayfishMockType.NONE)
                        var selectedCrayfishType by remember { mutableStateOf(crayfishTypes[5]) }
                        val crayfishTypesString = arrayOf("Szlachetny", "Amerykański", "Sygnałowy", "Błotny", "Niezweryfikowany")
                        var selectedCrayfishTypeString by remember { mutableStateOf(crayfishTypesString[0]) }

                        if (selectedMarkerType == MarkerMockType.CRAYFISH) {
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
                                            markerMockList.add(MarkerMock(lat, lng, title, description, currentDate, selectedMarkerType, selectedCrayfishType))
                                            openFormWindow.value = false
                                        }
                                    }
                                }
                            }
                        }) {
                            Text(text = "Dodaj znacznik")
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}