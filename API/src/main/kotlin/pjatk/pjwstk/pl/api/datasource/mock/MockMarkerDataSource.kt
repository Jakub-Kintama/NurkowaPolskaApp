package pjatk.pjwstk.pl.api.datasource.mock

import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.enums.CrayfishType
import pjatk.pjwstk.pl.api.model.LatLng
import pjatk.pjwstk.pl.api.model.MapMarker
import pjatk.pjwstk.pl.api.model.Marker
import java.util.*

@Repository
class MockMarkerDataSource : MarkerDataSource {

    val markers = listOf(
        Marker(1, MapMarker(LatLng(1.1, 1.1), "title 1", "description 1"), 1, CrayfishType.NOBLE, Date(), true),
        Marker(2, MapMarker(LatLng(2.2, 2.2), "title 2", "description 2"), 2, CrayfishType.AMERICAN, Date(), false),
        Marker(3, MapMarker(LatLng(3.3, 3.3), "title 3", "description 3"), 3, CrayfishType.SIGNAL, Date(), true)
    )

    override fun retrieveMarkers(): Collection<Marker> = markers
    override fun retrieveMarkerById(markerId: Int): Marker =
        markers.firstOrNull { it.id == markerId }
            ?: throw NoSuchElementException("Could not find a marker with id $markerId")    //TODO(temporary)
}