package com.example.nurkowapolskaapp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nurkowapolskaapp.api.ViewModelApi
import com.example.nurkowapolskaapp.map.model.CrayfishType
import com.example.nurkowapolskaapp.map.model.MapMarker
import com.example.nurkowapolskaapp.map.model.Marker
import com.example.nurkowapolskaapp.map.model.MarkerType
import com.example.nurkowapolskaapp.functions.base64ToBitmap
import com.example.nurkowapolskaapp.map.CircleShape
import com.example.nurkowapolskaapp.map.markerImage
import com.example.nurkowapolskaapp.signin.ViewModelAuth
import java.time.LocalDate

@Composable
fun MarkersList(viewModelAuth: ViewModelAuth, viewModelApi: ViewModelApi) {
    LaunchedEffect(viewModelApi) {
        viewModelApi.getMarkers()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp), horizontalArrangement = Arrangement.Center) {
            Text("Lista Twoich znaczników", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            LazyColumn {
                this.items(viewModelApi.markerList.value) { marker ->
                    if(marker.userEmail == viewModelAuth.currentUserMail.value) {
                        Column(modifier = Modifier.padding(8.dp, 4.dp)) {
                            EditMarkerInfoWindow(
                                marker,
                                viewModelApi
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EditMarkerInfoWindow(
    marker: Marker,
    viewModelApi: ViewModelApi
) {
    var showDetails by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            showDetails = !showDetails
                        }
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val imageModifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                Box {
                    if(marker.CrayfishType != CrayfishType.OTHER) {
                        CircleShape(color = Color.Red, size = 40)
                        Image(painter = painterResource(id = R.drawable.noun_crayfish_5090296), contentDescription = "", modifier = imageModifier)
                    }
                    else {
                        CircleShape(color = Color.Yellow, size = 40)
                        Image(painter = painterResource(id = R.drawable.noun_warning_2801479), contentDescription = "", modifier = imageModifier)
                    }
                }
                Text(text = "${marker.mapMarker.title} - ${marker.date}", textAlign = TextAlign.Center)
                Icon(imageVector = if (showDetails) {
                    Icons.Filled.KeyboardArrowUp
                } else {
                    Icons.Filled.KeyboardArrowDown
                }, contentDescription = null)

            }
            if(showDetails) {
                Spacer(modifier = Modifier.height(16.dp))
                MarkerDescription(marker, viewModelApi)
            }
        }
    }
}

@Composable
fun MarkerDescription(
    marker: Marker,
    viewModelApi: ViewModelApi
) {
    val showEditMarker = remember {
        mutableStateOf(false)
    }

    val openAlertDialogWindow = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        if(marker.image?.data != null) {
            val base64ImageBitmap = base64ToBitmap(marker.image.data)
            val imageBitmap = base64ImageBitmap!!.asImageBitmap()
            Image(bitmap = imageBitmap, contentDescription = "")
        } else {
            Box {
                val imageModifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                if(marker.CrayfishType != CrayfishType.OTHER) {
                    CircleShape(color = Color.White, size = 80)
                    Image(painter = painterResource(id = R.drawable.noun_crayfish_5090296), contentDescription = "", modifier = imageModifier)
                } else {
                    CircleShape(color = Color.White, size = 80)
                    Image(painter = painterResource(id = R.drawable.noun_warning_2801479), contentDescription = "", modifier = imageModifier)
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        val crayfishTypesString = arrayOf("Rak Szlachetny", "Rak Amerykański", "Rak Sygnałowy", "Rak Galicyjski", "Inne")
        val titleDescription = "OPIS - ${crayfishTypesString.getOrNull(marker.CrayfishType.ordinal) ?: crayfishTypesString.last()}"
        Text(text = titleDescription, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = marker.mapMarker.description)
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(onClick = { openAlertDialogWindow.value = true }) {
            Text("Usuń znacznik")
        }
        Button(onClick = { showEditMarker.value = !showEditMarker.value }) {
            Text("Edytuj znacznik")
        }
        if(showEditMarker.value) {
            UpdateMarkerInfo(showEditMarker = showEditMarker, marker = marker, viewModelApi = viewModelApi)
        }
        if(openAlertDialogWindow.value) {
            val markerId = marker.id
            DeleteMarkerAlertDialog(openAlertDialogWindow, markerId, viewModelApi)
        }
    }
}

@Composable
fun DeleteMarkerAlertDialog(
    openAlertDialogWindow: MutableState<Boolean>,
    markerId: String?,
    viewModelApi: ViewModelApi
) {
    AlertDialog(
        onDismissRequest = { openAlertDialogWindow.value = false },
        title = { Text("Potwierdź usunięcie") },
        text = { Text("Jesteś pewny że chcesz usunąć ten znacznik? Ten proces jest nieodwracalny") },
        confirmButton = {
            Button(
                onClick = {
                    markerId?.let { viewModelApi.deleteMarker(it, viewModelApi) }
                    viewModelApi.getMarkers()
                    openAlertDialogWindow.value = false
                }
            ) {
                Text("Usuń")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    openAlertDialogWindow.value = false
                }
            ) {
                Text("Anuluj")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMarkerInfo(
    showEditMarker: MutableState<Boolean>,
    marker: Marker,
    viewModelApi: ViewModelApi
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    when {
        showEditMarker.value -> {

            ModalBottomSheet(
                onDismissRequest = { showEditMarker.value = false },
                sheetState = sheetState,
            ) {
                Column {
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
                            var selectedMarkerType by remember {
                                mutableStateOf(
                                    if(marker.CrayfishType != CrayfishType.OTHER) {
                                        MarkerType.CRAYFISH
                                    } else {
                                        MarkerType.OTHER
                                    }
                                )
                            }
                            val markerTypesString = arrayOf("Rak", "Inne")
                            var selectedMarkerTypeString by remember {
                                if(marker.CrayfishType != CrayfishType.OTHER) {
                                    mutableStateOf(markerTypesString[0])
                                } else {
                                    mutableStateOf(markerTypesString[1])
                                }
                            }

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
                                var selectedCrayfishType by remember {
                                    when (marker.CrayfishType) {
                                        CrayfishType.NOBLE -> {
                                            mutableStateOf(CrayfishType.NOBLE)
                                        }
                                        CrayfishType.AMERICAN -> {
                                            mutableStateOf(CrayfishType.AMERICAN)
                                        }
                                        CrayfishType.SIGNAL -> {
                                            mutableStateOf(CrayfishType.SIGNAL)
                                        }
                                        else -> {
                                            mutableStateOf(CrayfishType.GALICIAN)
                                        }
                                    }
                                }
                                val crayfishTypesString = arrayOf("Szlachetny", "Amerykański", "Sygnałowy", "Błotny")
                                var selectedCrayfishTypeString by remember {
                                    when (marker.CrayfishType) {
                                        CrayfishType.NOBLE -> {
                                            mutableStateOf(crayfishTypesString[0])
                                        }
                                        CrayfishType.AMERICAN -> {
                                            mutableStateOf(crayfishTypesString[1])
                                        }
                                        CrayfishType.SIGNAL -> {
                                            mutableStateOf(crayfishTypesString[2])
                                        }
                                        else -> {
                                            mutableStateOf(crayfishTypesString[3])
                                        }
                                    }
                                }

                                if (selectedMarkerType == MarkerType.CRAYFISH) {
                                    Spacer(modifier = Modifier.height(25.dp))
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
                                var title by remember { mutableStateOf(marker.mapMarker.title) }
                                OutlinedTextField(
                                    value = title,
                                    onValueChange = {
                                        if(it.length <= maxChar) {
                                            title = it
                                        }
                                        if (it.length == maxChar) {
                                            Toast.makeText(
                                                context,
                                                "Osiągnięto limit znaków",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    label = { Text("Adres znacznika") },
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                var description by remember { mutableStateOf(marker.mapMarker.description) }
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

                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = {
                                        if(description.isNotEmpty()) {
                                            val image = markerImage(name = marker.userEmail, data = marker.image?.data)
                                            val markerObject = Marker(
                                                id = marker.id,
                                                mapMarker = MapMarker(
                                                    position = marker.mapMarker.position,
                                                    title = title,
                                                    description = description,
                                                ),
                                                userEmail = marker.userEmail,
                                                CrayfishType = selectedCrayfishType,
                                                date = LocalDate.now(),
                                                verified = marker.verified,
                                                image = image,
                                            )
                                            Log.d("MARKER", "$markerObject")
                                            viewModelApi.updateMarker(markerObject, viewModelApi)
                                            showEditMarker.value = false
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Opis nie może być pusty",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }) {
                                    Text(text = "Edytuj znacznik")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}