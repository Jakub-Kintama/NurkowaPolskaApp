package pjatk.pjwstk.pl.api.service

import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.model.Marker

@Service
class AdminMarkerService(private val dataSource: MarkerDataSource) {
    fun addMarker(marker: Marker): Marker = dataSource.createMarker(marker)
    fun updateMarker(marker: Marker): Marker = dataSource.updateMarker(marker)

}