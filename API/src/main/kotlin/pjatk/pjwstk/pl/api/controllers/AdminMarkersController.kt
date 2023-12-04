package pjatk.pjwstk.pl.api.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pjatk.pjwstk.pl.api.model.Admin
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.service.AdminMarkerService

@RestController
@RequestMapping("api")
class AdminMarkersController(private val service: AdminMarkerService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @PostMapping("/marker")
    @ResponseStatus(HttpStatus.CREATED)
    fun addMarker(@RequestBody marker: Marker): Marker = service.addMarker(marker)

    @PatchMapping("/marker")
    fun updateMarker(@RequestBody marker: Marker): Marker = service.updateMarker(marker)

    @DeleteMapping("/marker/{markerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMarker(@PathVariable markerId: String): Unit = service.deleteMarker(markerId)

    @GetMapping("/admins")
    fun getAdmins(): Collection<Admin> = service.getAdmins()

    @GetMapping("admin/{adminId}")
    fun getAdminById(@PathVariable adminId: String): Admin = service.getAdminById(adminId)

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    fun addAdmin(@RequestBody admin: Admin): Admin = service.addAdmin(admin)

    @DeleteMapping("/admin/{adminId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAdmin(@PathVariable adminId: String): Unit = service.deleteAdmin(adminId)
}