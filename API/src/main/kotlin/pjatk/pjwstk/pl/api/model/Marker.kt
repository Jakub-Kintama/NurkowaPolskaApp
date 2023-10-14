package pjatk.pjwstk.pl.api.model

import pjatk.pjwstk.pl.api.enums.CrayfishType
import java.util.*

data class Marker(
    val mapMarker: MapMarker,
    val userId: Int,
    val crayfishType: CrayfishType,
    val date: Date,
    val verified: Boolean
)