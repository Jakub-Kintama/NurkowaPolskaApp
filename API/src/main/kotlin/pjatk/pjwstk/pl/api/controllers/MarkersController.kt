package pjatk.pjwstk.pl.api.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.service.MarkerService
import java.time.LocalDate

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
    fun getMarkersByUserId(@PathVariable userId: Int): Collection<Marker> = service.getMarkersByUserId(userId)

    @GetMapping("/since/{since}")
    fun getMarkersSinceDate(@PathVariable since: LocalDate): Collection<Marker> = service.getMarkersSinceDate(since)

    @GetMapping("/since/{since}/to/{to}")
    fun getMarkersSinceDateToDate(@PathVariable since: LocalDate, @PathVariable to: LocalDate): Collection<Marker> =
        service.getMarkersSinceDateToDate(since, to)

    @GetMapping("/year/{year}")
    fun getMarkersByYear(@PathVariable year: Int): Collection<Marker> = service.getMarkersByYear(year)
}