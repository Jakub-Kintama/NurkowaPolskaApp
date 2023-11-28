package com.example.nurkowapolskaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nurkowapolskaapp.app.functions.FirstAid
import com.example.nurkowapolskaapp.app.functions.Insurance
import com.example.nurkowapolskaapp.app.functions.maps.AddMarker
import com.example.nurkowapolskaapp.app.functions.maps.CrayfishesType
import com.example.nurkowapolskaapp.app.functions.maps.Marker
import com.example.nurkowapolskaapp.app.functions.maps.MarkersMap
import com.example.nurkowapolskaapp.app.functions.maps.MarkersType
import com.example.nurkowapolskaapp.app.functions.maps.TestMap
import com.example.nurkowapolskaapp.app.functions.maps.markers
import com.example.nurkowapolskaapp.ui.theme.NurkowaPolskaAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NurkowaPolskaAppTheme {
                AppScaffold()
            }
        }
    }

}

// OptIn potrzebny bo z jakiegoś powodu BottomAppBar jest traktowany jako eksperymentalny feature
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "Default") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("homepage") {
                            popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(Icons.Filled.Home, null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        /*Google Sign in TODO*/
                    }) {
                        Icon(Icons.Filled.Person, null)
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding))
        {
            NavHost(navController, startDestination = "homepage") {
                composable("homepage") { Homepage(navController) }
                composable("mapOfMarkers") { TestMap(navController) }
                composable("insurance") { Insurance(navController) }
                composable("firstAid") { FirstAid(navController) }
                composable("addMarker") { AddMarker(navController) }
                composable("markersMap") { MarkersMap() }
            }
        }
    }
}



@Preview(showSystemUi = false, showBackground = false)
@Composable
fun AppScaffoldPreview() {
    AppScaffold()
}

