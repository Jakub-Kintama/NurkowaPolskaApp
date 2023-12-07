package pjatk.pjwstk.pl.api.controllers.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pjatk.pjwstk.pl.api.service.AuthenticationService

@RestController
@RequestMapping("/api/auth")
class AuthController(private val service: AuthenticationService) {

    @PostMapping
    fun authenticate(@RequestBody authRequest: AuthenticationRequest) : AuthenticationResponse =
        service.authentication(authRequest)
}