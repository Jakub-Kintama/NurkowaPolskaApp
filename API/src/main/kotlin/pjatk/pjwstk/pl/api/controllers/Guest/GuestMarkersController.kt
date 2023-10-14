package pjatk.pjwstk.pl.api.controllers.Guest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/markers")
class GuestMarkersController {
    @GetMapping
    fun getAllMarkers(): String{
        return "get all markers"
    }

    @GetMapping("/{id}")
    fun getMarkerById(@PathVariable id:String): String{
        return "get marker by $id"
    }

    @GetMapping("/user/{id}")
    fun getMarkersByUserId(@PathVariable id:String): String{
        return "get markers by user $id"
    }

    @GetMapping("/since/{since}")
    fun getMarkersSinceDate(@PathVariable since:String): String{
        return "get markers $since date"
    }

    @GetMapping("/since/{since}/to/{to}")
    fun getMarkersSinceDateToDate(@PathVariable since:String, @PathVariable to: String): String{
        return "get markers $since date $to date"
    }

    @GetMapping("/year/{year}")
    fun getMarkersFromYear(@PathVariable year: String): String{
        return "get markers by $year"
    }
}