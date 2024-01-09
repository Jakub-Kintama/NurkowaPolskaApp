package pjatk.pjwstk.pl.api.controllers.jwt

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import pjatk.pjwstk.pl.api.service.jwt.AuthenticationService

@RestController
@RequestMapping("/api/auth")
class AuthController(private val service: AuthenticationService) {

    @PostMapping
    fun authenticate(@RequestBody authRequest: AuthenticationRequest): AuthenticationResponse =
        service.authentication(authRequest)

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshTokenRequest): TokenResponse =
        service.refreshAccessToken(request.token)
            ?.mapToTokenResponse()
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token")


    private fun String.mapToTokenResponse(): TokenResponse =
        TokenResponse(token = this)
}