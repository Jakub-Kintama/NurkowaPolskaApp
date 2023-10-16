package pjatk.pjwstk.pl.api.datasource

import pjatk.pjwstk.pl.api.model.Marker


interface MarkerDataSource {

    fun retrieveMarkers(): Collection<Marker>
    fun retrieveMarkerById(markerId: Int): Marker

}