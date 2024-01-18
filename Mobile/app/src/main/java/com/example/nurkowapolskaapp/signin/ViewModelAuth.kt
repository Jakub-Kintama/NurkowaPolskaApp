package com.example.nurkowapolskaapp.signin

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nurkowapolskaapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class ViewModelAuth : ViewModel() {

    private var signInLauncher: ActivityResultLauncher<Intent>? = null

    private val _isUserSignedIn = mutableStateOf(false)
    val isUserSignedIn: State<Boolean> = _isUserSignedIn

    private val _currentUserDisplayName = mutableStateOf("")
    val currentUserDisplayName: State<String> = _currentUserDisplayName

    private val _currentUserPicture = mutableStateOf<Uri?>(null)
    val currentUserPicture: State<Uri?> = _currentUserPicture

    private val _currentUserMail = mutableStateOf("")
    val currentUserMail: State<String> = _currentUserMail

    private val _currentUserAccessToken = mutableStateOf("")
    val currentUserAccessToken: State<String> = _currentUserAccessToken

    private val _errorState = mutableStateOf<String?>(null)
    val errorState: State<String?> = _errorState

    fun startSignIn(activity: ComponentActivity) {
        signInLauncher?.launch(createSignInIntent(activity))
    }

    fun initSignInLauncher(activity: ComponentActivity) {
        signInLauncher = rememberSignInLauncher(activity)
    }

    private fun createSignInIntent(activity: ComponentActivity): Intent {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.OAUTH_CLIENT_ID))
            .requestProfile()
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, signInOptions)

        return googleSignInClient.signInIntent
    }

    private fun rememberSignInLauncher(activity: ComponentActivity): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleSignInResult(task)
        }
    }

    fun signOut(application: Application) {
        GoogleSignIn.getClient(application, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut()
        resetState()
    }

    private fun resetState() {
        _isUserSignedIn.value = false
        _currentUserDisplayName.value = ""
        _currentUserPicture.value = null
        _currentUserMail.value = ""
        _currentUserAccessToken.value = ""
        _errorState.value = null
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d("SignIn", "Sign-in successful. Account: ${account?.displayName}")
            _isUserSignedIn.value = true
            _currentUserDisplayName.value = account?.displayName ?: "Unknown User"
            _currentUserPicture.value = account?.photoUrl
            _currentUserMail.value = account?.email ?: "Unknown e-mail"
            _currentUserAccessToken.value = account?.idToken ?: ""
            Log.d("TOKEN_ID_MOBILE", account?.idToken.toString())

        } catch (e: ApiException) {
            e.printStackTrace()
            Log.e("SignIn", "Sign-in failed with error: ${e.message}")
            _errorState.value = "Sign-in failed: ${e.message}"
        }
    }
}