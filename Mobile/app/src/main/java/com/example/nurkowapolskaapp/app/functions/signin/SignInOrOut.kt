package com.example.nurkowapolskaapp.app.functions.signin

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nurkowapolskaapp.R
import kotlinx.coroutines.launch

@Composable
fun SignInOrOut(
    googleAuthUiClient: GoogleAuthUiClient,
    applicationContext: Context,
    userIsLogged: MutableState<Boolean>
) {
    val viewModel = viewModel<SignInViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    println(userIsLogged)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == Activity.RESULT_OK) {
                lifecycleOwner.lifecycleScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if(state.isSignInSuccessful) {
            Toast.makeText(
                applicationContext,
                "${googleAuthUiClient.getSignedInUser()?.email} zalogowany",
                Toast.LENGTH_LONG
            ).show()
            viewModel.resetState()
        }
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(!userIsLogged.value) {
                Text(text = "Zaloguj się z pomocą google", color = Color.Black, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                SignInButton(
                    onSignInClick = {
                        lifecycleOwner.lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                            userIsLogged.value = true
                        }
                    },
                )
            }
            if(userIsLogged.value) {
                Text(text = "Zaloguj się z pomocą google", color = Color.Black, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                SignOutSButton(
                    onSignOut = {
                        lifecycleOwner.lifecycleScope.launch {
                            googleAuthUiClient.signOut()
                            Toast.makeText(
                                applicationContext,
                                "Użytkownik został wylogowany",
                                Toast.LENGTH_LONG
                            ).show()
                            userIsLogged.value = false
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SignInButton(
    onSignInClick: () -> Unit
) {
    ClickableImageButton(
        drawableResId = R.drawable.android_light_rd_si,
        onClick = { onSignInClick.invoke() }
    )
}

@Composable
fun SignOutSButton(
    onSignOut: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .height(40.dp)
            .width(124.dp)
            .border(BorderStroke(1.dp, Color(7632757))),
        colors = ButtonDefaults.outlinedButtonColors(Color.White),
        onClick = { onSignOut.invoke() }
    ) {
        Text(text = "Wyloguj się", color = Color.Black)
    }
}

@Composable
fun ClickableImageButton(
    drawableResId: Int,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = drawableResId),
        contentDescription = null,
        modifier = Modifier.clickable { onClick.invoke() }
    )
}