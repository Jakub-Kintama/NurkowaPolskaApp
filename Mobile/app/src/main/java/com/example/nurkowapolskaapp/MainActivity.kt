package com.example.nurkowapolskaapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nurkowapolskaapp.api.ViewModelApi
import com.example.nurkowapolskaapp.signin.ViewModelAuth
import com.example.nurkowapolskaapp.ui.theme.NurkowaPolskaAppTheme

class MainActivity : ComponentActivity() {

    private val viewModelApi: ViewModelApi by lazy {
        ViewModelApi()
    }

    private val viewModelAuth: ViewModelAuth by lazy {
        ViewModelAuth()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelAuth.initSignInLauncher(this)

        viewModelApi.getMarkers()

        setContent {
            NurkowaPolskaAppTheme {
                AppScaffold(viewModelApi, viewModelAuth)
            }
        }
    }
}

// OptIn potrzebny bo z jakiegoś powodu BottomAppBar jest traktowany jako eksperymentalny feature
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    viewModelApi: ViewModelApi,
    viewModelAuth: ViewModelAuth,
) {
    val activity = LocalContext.current as ComponentActivity
    val isUserSignedIn by remember { viewModelAuth.isUserSignedIn }

    val navController = rememberNavController()
    rememberCoroutineScope()

    val sheetSignInState = rememberModalBottomSheetState()
    val showSignInSheet = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = stringResource(id = R.string.app_name)) },
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
                    IconButton(onClick = { showSignInSheet.value = true }) {
                        if(isUserSignedIn) {
                            AsyncImage(
                                viewModelAuth.currentUserPicture.value,
                                contentDescription = null,
                                modifier = Modifier.clip(CircleShape)
                            )
                        } else {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        if(showSignInSheet.value) {
            ModalBottomSheet(onDismissRequest = { showSignInSheet.value = false }, sheetState = sheetSignInState) {
                Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 34.dp)) {
                   SignInOutButton(activity, viewModelAuth, isUserSignedIn)
                }
            }
        }
        Column(Modifier.padding(innerPadding))
        {
            NavHost(navController, startDestination = "homepage") {
                composable("homepage") { Homepage(navController, isUserSignedIn, showSignInSheet) }
                composable("markersMap") { MarkersMap(viewModelApi, viewModelAuth, isUserSignedIn) }
                composable("markersList") { MarkersList(viewModelAuth, viewModelApi) }
            }
        }
    }
}

@Composable
fun SignInOutButton(
    activity: ComponentActivity,
    viewModelAuth: ViewModelAuth,
    isUserSignedIn: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isUserSignedIn) {
            SignedMessage(viewModelAuth.currentUserDisplayName.value, viewModelAuth)
        } else {
            Text("Zaloguj się Google kontem")
            Button(onClick = { viewModelAuth.startSignIn(activity) }) {
                Text("Zaloguj się")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SignedMessage(userName: String, viewModelAuth: ViewModelAuth) {
    Text("Jesteś zalogowany jako: $userName")
    val application = LocalContext.current.applicationContext as Application
    Button(onClick = {
        viewModelAuth.signOut(application)
    }) {
        Text("Wyloguj się")
    }
}