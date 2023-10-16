package pjatk.pjwstk.pl.api.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pjatk.pjwstk.pl.api.model.MapMarker

@RestController
@RequestMapping("api/marker")
class AdminMarkersController {
    @PostMapping("/add")
    fun getMarkerById(mapMarker: MapMarker): MapMarker {
        TODO("Not yet implemented")
    }
}