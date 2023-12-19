package com.example.nurkowapolskaapp.app.functions.signin

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
)