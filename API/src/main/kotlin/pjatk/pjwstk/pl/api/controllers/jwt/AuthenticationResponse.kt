package pjatk.pjwstk.pl.api.controllers.jwt

import org.springframework.security.core.GrantedAuthority

data class AuthenticationResponse(
    val email: String,
    val role: MutableCollection<out GrantedAuthority>,
    val accessToken: String,
    val refreshToken: String
)
