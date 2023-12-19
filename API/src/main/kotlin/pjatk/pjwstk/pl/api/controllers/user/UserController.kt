package pjatk.pjwstk.pl.api.controllers.user

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pjatk.pjwstk.pl.api.model.User
import pjatk.pjwstk.pl.api.service.UserService

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "jwtAuth")
class UserController(private val service: UserService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getUsers(): Collection<UserResponse> =
        service.getUsers().map { UserResponse(it.email, it.role) }

    @GetMapping("/{userEmail}")
    fun getUserByEmail(@PathVariable userEmail: String): UserResponse {
        val user = service.getUserByEmail(userEmail)
        return UserResponse(user.email, user.role)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody user: User): User = service.addUser(user)

    @DeleteMapping("/{userEmail}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable userEmail: String): Unit = service.deleteUser(userEmail)
}