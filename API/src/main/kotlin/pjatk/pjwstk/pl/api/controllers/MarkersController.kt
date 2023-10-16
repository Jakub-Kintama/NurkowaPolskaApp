package pjatk.pjwstk.pl.api.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.service.MarkerService

@RestController
@RequestMapping("api/markers")
class MarkersController(private val service: MarkerService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @GetMapping
    fun getMarkers(): Collection<Marker> = service.getMarkers()

    @GetMapping("/{markerId}")
    fun getMarkerById(@PathVariable markerId: Int): Marker = service.getMarkerById(markerId)

    @GetMapping("/user/{userId}")
    fun getMarkersByUserId(@PathVariable userId: String): String {
        return "get markers by user $userId"
    }

    @GetMapping("/since/{since}")
    fun getMarkersSinceDate(@PathVariable since: String): String {
        return "get markers $since date"
    }

    @GetMapping("/since/{since}/to/{to}")
    fun getMarkersSinceDateToDate(@PathVariable since: String, @PathVariable to: String): String {
        return "get markers $since date $to date"
    }

    @GetMapping("/year/{year}")
    fun getMarkersFromYear(@PathVariable year: String): String {
        return "get markers by $year"
    }
}