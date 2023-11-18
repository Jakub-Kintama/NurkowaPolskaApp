package pjatk.pjwstk.pl.api.datasource.mongodb

import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.model.Marker
import java.time.LocalDate
import java.util.*

@Repository("mongodb")
class MongodbMarkerDataSource : MarkerDataSource {
    override fun retrieveMarkers(): Collection<Marker> {
        TODO("Not yet implemented")
    }

    override fun retrieveMarkerById(markerId: String): Marker {
        TODO("Not yet implemented")
    }

    override fun retrieveMarkersByUserEmail(userEmail: String): Collection<Marker> {
        TODO("Not yet implemented")
    }

    override fun retrieveMarkersSinceDate(since: LocalDate): Collection<Marker> {
        TODO("Not yet implemented")
    }

    override fun retrieveMarkersSinceDateToDate(since: LocalDate, to: LocalDate): Collection<Marker> {
        TODO("Not yet implemented")
    }

    override fun retrieveMarkersByYear(year: Int): Collection<Marker> {
        TODO("Not yet implemented")
    }

    override fun createMarker(marker: Marker): Marker {
        TODO("Not yet implemented")
    }

    override fun updateMarker(marker: Marker): Marker {
        TODO("Not yet implemented")
    }

    override fun deleteMarker(markerId: String) {
        TODO("Not yet implemented")
    }

}
