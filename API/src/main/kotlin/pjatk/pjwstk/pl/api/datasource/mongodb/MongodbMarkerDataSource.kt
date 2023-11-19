package pjatk.pjwstk.pl.api.datasource.mongodb

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.model.LatLng
import pjatk.pjwstk.pl.api.model.MapMarker
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.model.enums.CrayfishType
import java.time.LocalDate
import java.util.*

@Repository("mongodb")
class MongodbMarkerDataSource(private val mongoTemplate: MongoTemplate) : MarkerDataSource {
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
        return mongoTemplate.save(marker)
    }

    override fun updateMarker(marker: Marker): Marker {
        TODO("Not yet implemented")
    }

    override fun deleteMarker(markerId: String) {
        TODO("Not yet implemented")
    }

}
