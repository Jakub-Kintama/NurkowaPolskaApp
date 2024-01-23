package com.example.nurkowapolskaapp.map.buttons

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.nurkowapolskaapp.api.ViewModelApi
import com.example.nurkowapolskaapp.functions.convertImageToBase64
import com.example.nurkowapolskaapp.functions.getAddress
import com.example.nurkowapolskaapp.map.model.CrayfishType
import com.example.nurkowapolskaapp.map.model.LatLng
import com.example.nurkowapolskaapp.map.model.MapMarker
import com.example.nurkowapolskaapp.map.model.Marker
import com.example.nurkowapolskaapp.map.model.MarkerType
import com.example.nurkowapolskaapp.signin.ViewModelAuth
import com.google.android.gms.location.LocationServices
import java.time.LocalDate

typealias markerImage = com.example.nurkowapolskaapp.map.model.Image

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMarker(
    openFormWindow: MutableState<Boolean>,
    permissionCheck: ManagedActivityResultLauncher<String, Boolean>,
    context: Context,
    viewModelApi: ViewModelApi,
    viewModelAuth: ViewModelAuth
) {
    FloatingActionButton(
        modifier = Modifier.padding(start = 0.dp, top = 0.dp, bottom = 15.dp, end = 15.dp),
        onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                openFormWindow.value = true
            } else {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder
                    .setMessage("Jeżeli chcesz dodać znacznik to musisz udostępnić aplikacji swoją precyzyjną lokację w celu dokładnego zamieszczenia znacznika na mapie.\n\nJeżeli taka opcja nie występuje to musisz to zmienić w \"Ustawienia\"")
                    .setTitle("Udostępnianie lokacji")
                    .setPositiveButton("Ok") { dialog, _ ->
                        dialog.cancel()
                        permissionCheck.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }
                val dialog: AlertDialog = builder.create()
                dialog.show()
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
                sheetState = sheetState,
            ) {
                if(sheetState.isVisible) {
                    LaunchedEffect(sheetState) {
                        sheetState.hasExpandedState
                    }
                }
                Box(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 34.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier
                        .padding(20.dp)
                        .fillMaxHeight()) {
                        var expandedMarkerType by remember { mutableStateOf(false) }
                        arrayOf(MarkerType.CRAYFISH, MarkerType.OTHER)
                        var selectedMarkerType by remember { mutableStateOf(MarkerType.CRAYFISH) }
                        val markerTypesString = arrayOf("Rak", "Inne")
                        var selectedMarkerTypeString by remember { mutableStateOf(markerTypesString[0]) }

                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
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
                                            selectedMarkerType = MarkerType.CRAYFISH
                                            selectedMarkerTypeString = markerTypesString[0]
                                            expandedMarkerType = false
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = "Inne") },
                                        onClick = {
                                            selectedMarkerType = MarkerType.OTHER
                                            selectedMarkerTypeString = markerTypesString[1]
                                            expandedMarkerType = false
                                        }
                                    )
                                }
                            }

                            var expandedCrayfishType by remember { mutableStateOf(false) }
                            arrayOf(CrayfishType.NOBLE, CrayfishType.AMERICAN, CrayfishType.SIGNAL, CrayfishType.GALICIAN)
                            var selectedCrayfishType by remember { mutableStateOf(CrayfishType.NOBLE) }
                            val crayfishTypesString = arrayOf("Szlachetny", "Amerykański", "Sygnałowy", "Błotny")
                            var selectedCrayfishTypeString by remember { mutableStateOf(crayfishTypesString[0]) }

                            if (selectedMarkerType == MarkerType.CRAYFISH) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

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
                                                    selectedCrayfishType = CrayfishType.NOBLE
                                                    selectedCrayfishTypeString = crayfishTypesString[0]
                                                    expandedCrayfishType = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text(text = "Amerykański") },
                                                onClick = {
                                                    selectedCrayfishType = CrayfishType.AMERICAN
                                                    selectedCrayfishTypeString = crayfishTypesString[1]
                                                    expandedCrayfishType = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text(text = "Sygnałowy") },
                                                onClick = {
                                                    selectedCrayfishType = CrayfishType.SIGNAL
                                                    selectedCrayfishTypeString = crayfishTypesString[2]
                                                    expandedCrayfishType = false
                                                }
                                            )
                                            DropdownMenuItem(
                                                text = { Text(text = "Błotny") },
                                                onClick = {
                                                    selectedCrayfishType = CrayfishType.GALICIAN
                                                    selectedCrayfishTypeString = crayfishTypesString[3]
                                                    expandedCrayfishType = false
                                                }
                                            )
                                        }
                                    }
                                }
                            } else if (selectedMarkerType == MarkerType.OTHER) {
                                selectedCrayfishType = CrayfishType.OTHER
                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            val maxChar = 280
                            var description by remember { mutableStateOf("") }
                            OutlinedTextField(
                                value = description,
                                onValueChange = {
                                    if(it.length <= maxChar) {
                                        description = it
                                    }
                                    if (it.length == maxChar) {
                                        Toast.makeText(
                                            context,
                                            "Osiągnięto limit znaków",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                label = { Text("Opis znacznika") },
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            var selectedImageUri by remember {
                                mutableStateOf<Uri?>(null)
                            }

                            val singlePhotoPicker = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.PickVisualMedia(),
                                onResult = { uri -> selectedImageUri = uri }
                            )

                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                if(selectedImageUri != null) {
                                    item {
                                        LazyRow(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            item {
                                                AsyncImage(
                                                    model = selectedImageUri,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .width(240.dp)
                                                        .height(240.dp),
                                                    contentScale = ContentScale.Crop
                                                )
                                            }
                                        }
                                    }
                                }
                                item {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                                        Button(onClick = { singlePhotoPicker.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        ) }) {
                                            if(selectedImageUri != null) {
                                                Text("Wybierz inne zdjęcie")
                                            } else {
                                                Text(text = "Wybierz zdjęcie")
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    if(selectedImageUri != null) {
                                        if(description.isNotEmpty()) {
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
                                                            val address = getAddress(lat, lng, context)
                                                            val currentPosition = LatLng(lat, lng)
                                                            val currentUserMail = viewModelAuth.currentUserMail.value
                                                            val imageBase64 = convertImageToBase64(selectedImageUri, context)
                                                            val image = markerImage(currentUserMail, imageBase64)
                                                            val markerObject = Marker(
                                                                mapMarker = MapMarker(
                                                                    position = currentPosition,
                                                                    title = address,
                                                                    description = description,
                                                                ),
                                                                userEmail = currentUserMail,
                                                                CrayfishType = selectedCrayfishType,
                                                                date = LocalDate.now(),
                                                                verified = false,
                                                                image = image,
                                                            )
                                                            Log.d("MARKER", "$markerObject")
                                                            viewModelApi.addMarker(markerObject, viewModelApi)
                                                            openFormWindow.value = false
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Opis nie może być pusty",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Załącz zdjęcie",
                                            Toast.LENGTH_SHORT
                                        ).show()
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