package com.example.nurkowapolskaapp.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nurkowapolskaapp.R
import com.example.nurkowapolskaapp.functions.base64ToBitmap
import com.example.nurkowapolskaapp.map.model.CrayfishType
import com.example.nurkowapolskaapp.map.model.MapMarker
import java.time.LocalDate

typealias markerImage = com.example.nurkowapolskaapp.map.model.Image

@Composable
fun CustomMarkerInfoWindow(
    marker: MapMarker,
    markerDate: LocalDate,
    crayfishType: CrayfishType,
    image: markerImage?
) {
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
                    .height(40.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val imageModifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                Box {
                    if(crayfishType != CrayfishType.OTHER) {
                        CircleShape(color = Color.Red, size = 40)
                        Image(painter = painterResource(id = R.drawable.noun_crayfish_5090296), contentDescription = "", modifier = imageModifier)
                    }
                    else {
                        CircleShape(color = Color.Yellow, size = 40)
                        Image(painter = painterResource(id = R.drawable.noun_warning_2801479), contentDescription = "", modifier = imageModifier)
                    }
                }
                Text(text = "${marker.title} - $markerDate", textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if(image?.data != null) {
                    val base64ImageBitmap = base64ToBitmap(image.data)
                    val imageBitmap = base64ImageBitmap!!.asImageBitmap()
                    Image(bitmap = imageBitmap, contentDescription = "")
                } else {
                    Box {
                        val imageModifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                        if(crayfishType != CrayfishType.OTHER) {
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
            MarkerDescription(marker.description, crayfishType)
        }
    }
}

@Composable
fun MarkerDescription(description: String, crayfishType: CrayfishType) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        val crayfishTypesString = arrayOf("Rak Szlachetny", "Rak Amerykański", "Rak Sygnałowy", "Rak Galicyjski", "Inne")
        val titleDescription = "OPIS - ${crayfishTypesString.getOrNull(crayfishType.ordinal) ?: crayfishTypesString.last()}"
        Text(text = titleDescription, fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = description)
    }
}

@Composable
fun CircleShape(color: Color, size: Int) {
    Box(
        modifier = Modifier
            .run {
                if (size > 0) {
                    this.size(size.dp)
                } else {
                    this
                }
            }
            .background(color, MaterialTheme.shapes.small)
    )
}