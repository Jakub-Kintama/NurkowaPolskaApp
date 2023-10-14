package pjatk.pjwstk.pl.api.service

import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.datasource.MarkerDataSource
import pjatk.pjwstk.pl.api.model.Marker

@Service
class MarkerService(private val dataSource: MarkerDataSource) {
    fun getMarkers(): Collection<Marker> = dataSource.retrieveMarkers()
}