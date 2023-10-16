package pjatk.pjwstk.pl.api.model

import pjatk.pjwstk.pl.api.enums.CrayfishType
import java.time.LocalDate
import java.util.*

data class Marker(
    val id: Int,
    val mapMarker: MapMarker,
    val userId: Int,
    val crayfishType: CrayfishType,
    val date: LocalDate,
    val verified: Boolean
)