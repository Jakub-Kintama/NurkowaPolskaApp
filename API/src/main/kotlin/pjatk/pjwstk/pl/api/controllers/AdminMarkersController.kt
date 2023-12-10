package pjatk.pjwstk.pl.api.controllers

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.service.AdminMarkerService

@RestController
@RequestMapping("/api/markers")
@SecurityRequirement(name = "jwtAuth")
class AdminMarkersController(private val service: AdminMarkerService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addMarker(@RequestBody marker: Marker): Marker = service.addMarker(marker)

    @PatchMapping
    fun updateMarker(@RequestBody marker: Marker): Marker = service.updateMarker(marker)

    @DeleteMapping("/{markerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMarker(@PathVariable markerId: String): Unit = service.deleteMarker(markerId)


}