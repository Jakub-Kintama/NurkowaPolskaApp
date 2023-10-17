package pjatk.pjwstk.pl.api.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.service.AdminMarkerService

@RestController
@RequestMapping("api/marker")
class AdminMarkersController(private val service: AdminMarkerService) {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addMarker(@RequestBody marker: Marker): Marker = service.addMarker(marker)
}