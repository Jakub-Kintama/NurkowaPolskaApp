package pjatk.pjwstk.pl.api.controllers.admin

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pjatk.pjwstk.pl.api.model.Marker

@RestController
@RequestMapping("api/marker")
class AdminMarkersController {
    @PostMapping("/add")
    fun getMarkerById(marker: Marker): Marker {
        return "get marker by id"
    }
}