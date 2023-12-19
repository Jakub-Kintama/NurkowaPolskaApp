package com.example.nurkowapolskaapp

import android.content.Context
import android.os.Bundle
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
import com.example.nurkowapolskaapp.app.functions.map.MarkersMap
import com.example.nurkowapolskaapp.app.functions.signin.GoogleAuthUiClient
import com.example.nurkowapolskaapp.app.functions.signin.SignInOrOut
import com.example.nurkowapolskaapp.ui.theme.NurkowaPolskaAppTheme
import com.google.android.gms.auth.api.identity.Identity

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val userIsLogged = remember { mutableStateOf(
                googleAuthUiClient.getSignedInUser()?.userId != null
            ) }
            NurkowaPolskaAppTheme {
                AppScaffold(googleAuthUiClient, applicationContext, userIsLogged)
            }
        }
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
