package pjatk.pjwstk.pl.api.controllers.auth

data class AuthenticationRequest(
    val email: String,
    val password: String
)
