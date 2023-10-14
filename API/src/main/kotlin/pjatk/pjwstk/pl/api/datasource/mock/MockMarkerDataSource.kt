package pjatk.pjwstk.pl.api.datasource.mock

import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.enums.CrayfishType
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.model.LatLng
import pjatk.pjwstk.pl.api.model.MapMarker
import java.util.*

@Repository
class MockMarkerDataSource: MarkerDataSource {

    val markers = listOf(
        Marker(MapMarker(1, LatLng(1.1, 1.1), "title 1", "description 1"), 1, CrayfishType.NOBLE, Date(), true),
        Marker(MapMarker(2, LatLng(2.2, 2.2), "title 2", "description 2"), 1, CrayfishType.AMERICAN, Date(), false),
        Marker(MapMarker(3, LatLng(3.3, 3.3), "title 3", "description 3"), 3, CrayfishType.SIGNAL, Date(), true)
    )

    override fun retrieveMarkers(): Collection<Marker> = markers
}