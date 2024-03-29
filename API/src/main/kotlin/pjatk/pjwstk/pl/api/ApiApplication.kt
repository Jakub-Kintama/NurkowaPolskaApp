package pjatk.pjwstk.pl.api

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import pjatk.pjwstk.pl.api.model.responses.UserResponse
import pjatk.pjwstk.pl.api.model.enums.Role
import pjatk.pjwstk.pl.api.service.oauth2.GoogleOAuth2User


@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}

@RestController
class ApiController {

    @GetMapping("/ping")
    fun ping(): String {
        return "Server is up and running!"
    }

    @GetMapping("/me")
    @SecurityRequirements(
        SecurityRequirement(name = "jwtAuth"),
        SecurityRequirement(name = "oauth2")
    )
    fun getMyInfo(): UserResponse {
        return when (val principal = SecurityContextHolder.getContext().authentication.principal) {
            is GoogleOAuth2User -> UserResponse(principal.getUser().email, principal.getUser().role)
            is User -> UserResponse(principal.username,
                principal.authorities.firstOrNull().let { Role.valueOf(it.toString().removePrefix("ROLE_")) })
            else -> UserResponse("Unknown user", Role.NONE)
        }
    }
}