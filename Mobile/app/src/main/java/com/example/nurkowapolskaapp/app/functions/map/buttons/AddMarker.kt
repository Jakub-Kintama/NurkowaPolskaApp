package com.example.nurkowapolskaapp.app.functions.map.buttons

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.nurkowapolskaapp.app.functions.map.CrayfishMockType
import com.example.nurkowapolskaapp.app.functions.map.MarkerMock
import com.example.nurkowapolskaapp.app.functions.map.MarkerMockType
import com.example.nurkowapolskaapp.app.functions.map.functions.getCurrentDate
import com.example.nurkowapolskaapp.app.functions.map.markerMockList
import com.google.android.gms.location.LocationServices

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
                    val markerTypesString = arrayOf("Rak", "Niebezpieczeństwo", "Zanieczyszczenie")
                    var selectedMarkerTypeString by remember { mutableStateOf(markerTypesString[0]) }

                    Column {
                        Text("Typ znacznika:")
                        Spacer(Modifier.height(4.dp))
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
                                            /*  TODO: Get Location Name */
                                            val title = "Nazwa Lokacji"
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