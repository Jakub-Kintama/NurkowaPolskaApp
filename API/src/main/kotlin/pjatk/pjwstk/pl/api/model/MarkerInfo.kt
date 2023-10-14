package pjatk.pjwstk.pl.api.model

import pjatk.pjwstk.pl.api.enums.CrayfishType
import java.awt.Color
import java.util.*

data class MarkerInfo(
    val userId: String,
    val color: Color,
    val crayfishType: CrayfishType,
    val date: Date,
    val marker: Marker,
    val verified: Boolean
)