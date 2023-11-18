package pjatk.pjwstk.pl.api.model

import com.fasterxml.jackson.annotation.JsonFormat
import pjatk.pjwstk.pl.api.enums.CrayfishType
import java.time.LocalDate
import java.util.*

data class Marker(
    val id: Int,
    val mapMarker: MapMarker,
    val userId: Int,
    val crayfishType: CrayfishType,
    @JsonFormat(pattern="yyyy-MM-dd")
    val date: LocalDate,
    val verified: Boolean
)