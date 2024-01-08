package pjatk.pjwstk.pl.api

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

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
    fun getMyInfo(): String = SecurityContextHolder.getContext().authentication.authorities.toString()
}