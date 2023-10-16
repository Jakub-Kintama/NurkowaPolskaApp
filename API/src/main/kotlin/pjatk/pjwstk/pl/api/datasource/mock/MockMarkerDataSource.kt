package pjatk.pjwstk.pl.api.datasource.mock

import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.enums.CrayfishType
import pjatk.pjwstk.pl.api.model.LatLng
import pjatk.pjwstk.pl.api.model.MapMarker
import pjatk.pjwstk.pl.api.model.Marker
import java.time.LocalDate
import java.util.*

@Repository
class MockMarkerDataSource : MarkerDataSource {

    val markers = listOf(
        Marker(1, MapMarker(LatLng(1.1, 1.1), "title 1", "description 1"), 1, CrayfishType.NOBLE, LocalDate.parse("2023-10-10"), true),
        Marker(2, MapMarker(LatLng(2.2, 2.2), "title 2", "description 2"), 2, CrayfishType.AMERICAN, LocalDate.parse("2023-10-13"), false),
        Marker(3, MapMarker(LatLng(3.3, 3.3), "title 3", "description 3"), 3, CrayfishType.SIGNAL, LocalDate.parse("2023-10-16"), true),
        Marker(4, MapMarker(LatLng(4.4, 4.4), "title 4", "description 4"), 3, CrayfishType.GALICIAN, LocalDate.parse("2023-10-17"), true)
    )

    override fun retrieveMarkers(): Collection<Marker> = markers
    override fun retrieveMarkerById(markerId: Int): Marker =
        markers.firstOrNull { it.id == markerId }
            ?: throw NoSuchElementException("Could not find a marker with id $markerId")    //TODO(temporary)
    override fun retrieveMarkersByUserId(userId: Int): Collection<Marker> = markers.filter { it.userId == userId }
    override fun retrieveMarkersSinceDate(since: LocalDate): Collection<Marker> = markers.filter { it.date >= since }
    override fun retrieveMarkersSinceDateToDate(since: LocalDate, to: LocalDate): Collection<Marker> = markers.filter { it.date in since..to }
    override fun retrieveMarkersByYear(year: Int): Collection<Marker> = markers.filter { it.date.year == year }
}