package com.example.nurkowapolskaapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nurkowapolskaapp.app.functions.FirstAid
import com.example.nurkowapolskaapp.app.functions.Insurance
import com.example.nurkowapolskaapp.app.functions.map.Marker
import com.example.nurkowapolskaapp.app.functions.map.MarkersMap
import com.example.nurkowapolskaapp.app.functions.map.markerList
import com.example.nurkowapolskaapp.app.functions.signin.GoogleAuthUiClient
import com.example.nurkowapolskaapp.app.functions.signin.SignInOrOut
import com.example.nurkowapolskaapp.domain.MarkersApi
import com.example.nurkowapolskaapp.ui.theme.NurkowaPolskaAppTheme
import com.google.android.gms.auth.api.identity.Identity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }


    private val BASE_URL ="http://localhost:8080/"
    private val TAG ="CHECK_RESPONSE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAllMarkers()

        setContent {
            val userIsLogged = remember { mutableStateOf(
                googleAuthUiClient.getSignedInUser()?.userId != null
            ) }
            NurkowaPolskaAppTheme {
                AppScaffold(googleAuthUiClient, applicationContext, userIsLogged)
            }
        }
    }

    private fun getAllMarkers() {
        val apiMarkers = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarkersApi::class.java)

        apiMarkers.getMarkers().enqueue(object : Callback<List<Marker>>{
            override fun onResponse(call: Call<List<Marker>>, response: Response<List<Marker>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for(marker in it) {
                            markerList.add(
                                Marker(
                                    _id = marker._id,
                                    mapMarker = marker.mapMarker,
                                    userEmail = marker.userEmail,
                                    crayfishType = marker.crayfishType,
                                    date = marker.date,
                                    verified = marker.verified,
                                    image = marker.image
                                )
                            )
                        }
                        Log.i(TAG, "onResponse: success")
                    }
                }
            }

            override fun onFailure(call: Call<List<Marker>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })
    }
}

// OptIn potrzebny bo z jakiegoś powodu BottomAppBar jest traktowany jako eksperymentalny feature
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(googleAuthUiClient: GoogleAuthUiClient, applicationContext: Context, userIsLogged: MutableState<Boolean>) {
    val navController = rememberNavController()
    rememberCoroutineScope()


    val sheetSignInState = rememberModalBottomSheetState()
    var showSignInSheet by remember { mutableStateOf(false) }

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
                    IconButton(onClick = { showSignInSheet = true }) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                    }
                }
            )
        },
    ) { innerPadding ->
        if(showSignInSheet) {
            ModalBottomSheet(onDismissRequest = { showSignInSheet = false }, sheetState = sheetSignInState) {
                Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 34.dp)) {
                    SignInOrOut(
                        googleAuthUiClient = googleAuthUiClient,
                        applicationContext = applicationContext,
                        userIsLogged = userIsLogged
                    )
                }
            }
        }
        Column(Modifier.padding(innerPadding))
        {
            NavHost(navController, startDestination = "homepage") {
                composable("homepage") { Homepage(navController) }
                composable("insurance") { Insurance(navController) }
                composable("firstAid") { FirstAid() }
                composable("markersMap") { MarkersMap(googleAuthUiClient) }
            }
        }
    }
}
