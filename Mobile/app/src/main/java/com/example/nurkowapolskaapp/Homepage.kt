package com.example.nurkowapolskaapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nurkowapolskaapp.app.functions.map.CrayfishMockType
import com.example.nurkowapolskaapp.app.functions.map.MarkerMock
import com.example.nurkowapolskaapp.app.functions.map.MarkerMockType
import com.example.nurkowapolskaapp.app.functions.map.markerMockList

@Composable
fun Homepage(navController: NavController) {
    // Mock data
    markerMockList.add(
        MarkerMock(
            52.22977,
            21.01178,
            "Rak",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "14/10/2023",
            MarkerMockType.CRAYFISH,
            CrayfishMockType.MUD
        )
    )
    markerMockList.add(
        MarkerMock(
            54.35205,
            18.64637,
            "Zagrożenie",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "14/10/2023",
            MarkerMockType.DANGER,
            CrayfishMockType.NONE
        )
    )
    markerMockList.add(
        MarkerMock(
            53.9293,
            18.8613,
            "Zanieczyszczenie",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "14/10/2023",
            MarkerMockType.POLLUTION,
            CrayfishMockType.NONE
        )
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                modifier = Modifier
                    .width(180.dp)
                    .height(60.dp),
                onClick = {
                    navController.navigate("markersMap")
                }) {
                Text(text = "Mapa Znaczników")
            }
        }
        Spacer(modifier = Modifier.size(100.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                modifier = Modifier
                    .width(180.dp)
                    .height(60.dp),
                onClick = { navController.navigate("insurance") }) {
                Text(text = "Kalkulator Składki")
            }

            Button(
                modifier = Modifier
                    .width(180.dp)
                    .height(60.dp),
                onClick = { navController.navigate("firstAid") }) {
                Text(text = "Pierwsza Pomoc")
            }
        }
    }
}

@Preview
@Composable
fun HomepagePreview() {
    Homepage(rememberNavController())
}