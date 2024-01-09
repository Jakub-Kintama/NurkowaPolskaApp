package pjatk.pjwstk.pl.api.controllers.jwt

data class AuthenticationRequest(
    val email: String,
    val password: String
)
