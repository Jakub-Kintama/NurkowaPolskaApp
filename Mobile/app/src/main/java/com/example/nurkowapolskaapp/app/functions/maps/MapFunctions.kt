package com.example.nurkowapolskaapp.app.functions.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@Composable
fun AddMarkerButton(navController: NavController) {
    FloatingActionButton(onClick = {
        navController.popBackStack()
        navController.navigate("addMarker")
    }) {
        Icon(Icons.Filled.Add, contentDescription = null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMarker(navController: NavController) {
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        Arrangement.SpaceEvenly
    ) {

        val markerTypes = arrayOf(MarkerMockType.CRAYFISH, MarkerMockType.DANGER, MarkerMockType.POLLUTION)
        var expanded by remember { mutableStateOf(false) }
        var selectedMarker by remember { mutableStateOf(markerTypes[0]) }
        val markerTexts = arrayOf("Rak", "Niebezpieczeństwo", "Zanieczyszczenie")
        var selectedMarkerText by remember { mutableStateOf(markerTexts[0]) }
        Box(
            contentAlignment = Alignment.Center
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedMarkerText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor(),
                )

                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Rak") },
                        onClick = {
                            selectedMarker = markerTypes[0]
                            selectedMarkerText = markerTexts[0]
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Niebezpieczeństwo") },
                        onClick = {
                            selectedMarker = markerTypes[1]
                            selectedMarkerText = markerTexts[1]
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Zanieczyszczenie") },
                        onClick = {
                            selectedMarker = markerTypes[2]
                            selectedMarkerText = markerTexts[2]
                        }
                    )
                }
            }
        }

        var title by remember { mutableStateOf("") }
        OutlinedTextField(value = title, onValueChange = { title = it}, label = { Text("Nazwa lokacji znacznika") })
        var snippet by remember { mutableStateOf("") }
        OutlinedTextField(value = snippet, onValueChange = { snippet = it}, label = { Text("Opis znacznika") })

        Button(onClick = {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> {
                    navController.popBackStack()
                    navController.navigate("mapOfMarkers")
                    val userLocation = LocationServices.getFusedLocationProviderClient(context)
                    userLocation.lastLocation.addOnSuccessListener {
                            location: Location? ->
                        if(location != null) {
                            val latitude = location.latitude
                            val longitude = location.longitude
                            val currentDate = getCurrentDate()
                            markerMockList.add(MarkerMock(latitude, longitude, title, snippet, currentDate ,selectedMarker, CrayfishMockType.NONE ))
                        }
                    }
                } else -> {
                permissionCheck.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }

        }) {
            Text(text = "Dodaj Marker")
        }
    }
}

@Preview
@Composable
fun PreviewMarkerAddTest() {
    AddMarker(rememberNavController())
}

fun getCurrentDate(): String {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        currentDateTime.format(formatter)
    } else {
        val calendar = Calendar.getInstance().time
//        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatter.format(calendar)
    }
}
