package pjatk.pjwstk.pl.api.datasource.mock

import org.bson.types.ObjectId
import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.enums.CrayfishType
import pjatk.pjwstk.pl.api.model.LatLng
import pjatk.pjwstk.pl.api.model.MapMarker
import pjatk.pjwstk.pl.api.model.Marker
import java.time.LocalDate

@Repository("mock")
class MockMarkerDataSource : MarkerDataSource {

    val markers = mutableListOf(
        Marker(
            "000000000000000000000001",
            MapMarker(LatLng(1.1, 1.1), "title 1", "description 1"),
            "1",
            CrayfishType.NOBLE,
            LocalDate.of(2023, 10, 10),
            true
        ), Marker(
            "000000000000000000000002",
            MapMarker(LatLng(2.2, 2.2), "title 2", "description 2"),
            "2",
            CrayfishType.AMERICAN,
            LocalDate.of(2023, 10, 13),
            false
        ), Marker(
            "000000000000000000000003",
            MapMarker(LatLng(3.3, 3.3), "title 3", "description 3"),
            "3",
            CrayfishType.SIGNAL,
            LocalDate.of(2023, 10, 16),
            true
        ), Marker(
            "000000000000000000000004",
            MapMarker(LatLng(4.4, 4.4), "title 4", "description 4"),
            "3",
            CrayfishType.GALICIAN,
            LocalDate.of(2023, 10, 17),
            true
        )
    )

    override fun retrieveMarkers(): Collection<Marker> = markers
    override fun retrieveMarkerById(markerId: String): Marker = markers.firstOrNull { it.id == markerId }
        ?: throw NoSuchElementException("Could not find a marker with id ${markerId}.")    //TODO(temporary)

    override fun retrieveMarkersByUserEmail(userEmail: String): Collection<Marker> = markers.filter { it.userEmail == userEmail }
    override fun retrieveMarkersSinceDate(since: LocalDate): Collection<Marker> = markers.filter { it.date >= since }
    override fun retrieveMarkersSinceDateToDate(since: LocalDate, to: LocalDate): Collection<Marker> =
        markers.filter { it.date in since..to }

    override fun retrieveMarkersByYear(year: Int): Collection<Marker> = markers.filter { it.date.year == year }
    override fun createMarker(marker: Marker): Marker {
        if (markers.any { it.id == marker.id }) {
            throw IllegalArgumentException("Marker with id ${marker.id} already exists.")
        }
        markers.add(marker)
        return marker
    }

    override fun updateMarker(marker: Marker): Marker {
        val currentMarker = markers.firstOrNull { it.id == marker.id }
            ?: throw NoSuchElementException("Could not find a marker with id ${marker.id}.")

        markers.remove(currentMarker)
        markers.add(marker)

        return marker
    }

    override fun deleteMarker(markerId: String) {
        val currentMarker = markers.firstOrNull { it.id == markerId }
            ?: throw NoSuchElementException("Could not find a marker with id ${markerId}.")

        markers.remove(currentMarker)
    }
}