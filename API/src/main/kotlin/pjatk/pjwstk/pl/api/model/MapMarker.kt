package pjatk.pjwstk.pl.api.model

data class MapMarker(
    val id: Int,
    val position: LatLng,
    val title: String,
    val description: String
)