package pjatk.pjwstk.pl.api.datasource

import pjatk.pjwstk.pl.api.model.Marker
import java.time.LocalDate


interface MarkerDataSource {

    fun retrieveMarkers(): Collection<Marker>
    fun retrieveMarkerById(markerId: Int): Marker
    fun retrieveMarkersByUserId(userId: Int): Collection<Marker>
    fun retrieveMarkersSinceDate(since: LocalDate): Collection<Marker>
    fun retrieveMarkersSinceDateToDate(since: LocalDate, to: LocalDate): Collection<Marker>
    fun retrieveMarkersByYear(year: Int): Collection<Marker>
    fun createMarker(marker: Marker): Marker
    fun updateMarker(marker: Marker): Marker
    fun deleteMarker(markerId: Int)
}