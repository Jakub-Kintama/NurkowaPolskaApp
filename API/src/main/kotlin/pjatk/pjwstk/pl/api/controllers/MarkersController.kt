package pjatk.pjwstk.pl.api.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityRequirements
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import pjatk.pjwstk.pl.api.model.database.Marker
import pjatk.pjwstk.pl.api.service.MarkerService
import pjatk.pjwstk.pl.api.service.oauth2.GoogleOAuth2User
import java.time.LocalDate

@RestController
@RequestMapping("/api/markers")
class MarkersController(private val service: MarkerService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @GetMapping
    fun getMarkers(): Collection<Marker> = service.getMarkers()

    @GetMapping("/{markerId}")
    fun getMarkerById(@PathVariable markerId: String): Marker = service.getMarkerById(markerId)

    @GetMapping("/user/{userEmail}")
    fun getMarkersByUserEmail(@PathVariable userEmail: String): Collection<Marker> =
        service.getMarkersByUserEmail(userEmail)

    @GetMapping("/since/{since}")
    fun getMarkersSinceDate(@PathVariable since: LocalDate): Collection<Marker> = service.getMarkersSinceDate(since)

    @GetMapping("/since/{since}/to/{to}")
    fun getMarkersSinceDateToDate(@PathVariable since: LocalDate, @PathVariable to: LocalDate): Collection<Marker> =
        service.getMarkersSinceDateToDate(since, to)

    @GetMapping("/year/{year}")
    fun getMarkersByYear(@PathVariable year: Int): Collection<Marker> = service.getMarkersByYear(year)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirements(
        SecurityRequirement(name = "jwtAuth"),
        SecurityRequirement(name = "oauth2")
    )
    fun addMarker(@RequestBody marker: Marker): Marker = service.addMarker(Marker(
        id = marker.id,
        mapMarker = marker.mapMarker,
        userEmail = marker.userEmail,
        crayfishType = marker.crayfishType,
        date = marker.date,
        false,  //change to false
        image = marker.image
    ))

    @PatchMapping
    @SecurityRequirements(
        SecurityRequirement(name = "jwtAuth"),
        SecurityRequirement(name = "oauth2")
    )
    fun updateMarker(@RequestBody marker: Marker): Marker {
        val userEmail = when (val principal = SecurityContextHolder.getContext().authentication.principal) {
            is GoogleOAuth2User -> principal.getUser().email
            is User -> principal.username
            else -> "Unknown user"
        }
        if (userEmail != marker.userEmail) throw AccessDeniedException("You can only update your own markers.")

        return service.updateMarker(Marker(
            id = marker.id,
            mapMarker = marker.mapMarker,
            userEmail = marker.userEmail,
            crayfishType = marker.crayfishType,
            date = marker.date,
            false,  //change to false
            image = marker.image
        ))
    }

    @DeleteMapping("/{markerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirements(
        SecurityRequirement(name = "jwtAuth"),
        SecurityRequirement(name = "oauth2")
    )
    fun deleteMarker(@PathVariable markerId: String) {
        val userEmail = when (val principal = SecurityContextHolder.getContext().authentication.principal) {
            is GoogleOAuth2User -> principal.getUser().email
            is User -> principal.username
            else -> "Unknown user"
        }
        val marker = service.getMarkerById(markerId)
        if (userEmail != marker.userEmail) throw AccessDeniedException("You can only delete your own markers.")

        return service.deleteMarker(markerId)
    }
}