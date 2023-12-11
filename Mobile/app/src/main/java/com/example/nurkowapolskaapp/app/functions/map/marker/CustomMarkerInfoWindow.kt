package com.example.nurkowapolskaapp.app.functions.map.marker

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nurkowapolskaapp.R
import com.example.nurkowapolskaapp.app.functions.map.CrayfishMockType
import com.example.nurkowapolskaapp.app.functions.map.MarkerMock
import com.example.nurkowapolskaapp.app.functions.map.MarkerMockType

@Composable
fun CustomMarkerInfoWindow(
    markerMock: MarkerMock
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
                when (markerMock.markerType) {
                    MarkerMockType.CRAYFISH -> {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder_crayfish),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = imageModifier
                        )
                    }
                    MarkerMockType.DANGER -> {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder_danger),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = imageModifier
                        )
                    }
                    MarkerMockType.POLLUTION -> {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder_pollution),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = imageModifier
                        )
                    }
                }
                Text(text = "${markerMock.title} - ${markerMock.date}", textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painterResource(R.drawable.placeholder_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(160.dp)
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "OPIS", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = markerMock.description)
            }
        }
    }
}


val test = MarkerMock(52.22977, 21.01178, "Rak", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", "14/10/2023", MarkerMockType.CRAYFISH, CrayfishMockType.MUD)

@Preview
@Composable
fun CustomMarkerInfoWindowPreview() {
    CustomMarkerInfoWindow(test)
}