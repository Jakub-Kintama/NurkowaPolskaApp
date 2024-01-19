package pjatk.pjwstk.pl.api.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.model.database.Marker
import java.time.LocalDate

@Service
class MarkerService(@Qualifier("mongodbMarker") private val dataSource: MarkerDataSource) {
    fun getMarkers(): Collection<Marker> = dataSource.retrieveMarkers()
    fun getMarkerById(markerId: String): Marker = dataSource.retrieveMarkerById(markerId)
    fun getMarkersByUserEmail(userEmail: String): Collection<Marker> = dataSource.retrieveMarkersByUserEmail(userEmail)
    fun getMarkersSinceDate(since: LocalDate): Collection<Marker> = dataSource.retrieveMarkersSinceDate(since)
    fun getMarkersSinceDateToDate(since: LocalDate, to: LocalDate): Collection<Marker> =
        dataSource.retrieveMarkersSinceDateToDate(since, to)
    fun getMarkersByYear(year: Int): Collection<Marker> = dataSource.retrieveMarkersByYear(year)
    fun addMarker(marker: Marker): Marker = dataSource.createMarker(marker)
    fun updateMarker(marker: Marker): Marker = dataSource.updateMarker(marker)
    fun deleteMarker(markerId: String): Unit = dataSource.deleteMarker(markerId)
}