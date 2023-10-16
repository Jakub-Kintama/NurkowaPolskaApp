package pjatk.pjwstk.pl.api.service

import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.model.Marker
import java.time.LocalDate

@Service
class MarkerService(private val dataSource: MarkerDataSource) {
    fun getMarkers(): Collection<Marker> = dataSource.retrieveMarkers()
    fun getMarkerById(markerId: Int): Marker = dataSource.retrieveMarkerById(markerId)
    fun getMarkersByUserId(userId: Int): Collection<Marker> = dataSource.retrieveMarkersByUserId(userId)
    fun getMarkersSinceDate(since: LocalDate): Collection<Marker> = dataSource.retrieveMarkersSinceDate(since)
    fun getMarkersSinceDateToDate(since: LocalDate, to: LocalDate): Collection<Marker> = dataSource.retrieveMarkersSinceDateToDate(since, to)
    fun getMarkersByYear(year: Int): Collection<Marker> = dataSource.retrieveMarkersByYear(year)
}