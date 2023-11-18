package pjatk.pjwstk.pl.api.model

import com.fasterxml.jackson.annotation.JsonFormat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import pjatk.pjwstk.pl.api.enums.CrayfishType
import java.time.LocalDate

@Document
data class Marker(
    @Id
    val id: String? = null,
    val mapMarker: MapMarker,
    val userEmail: String,
    val crayfishType: CrayfishType,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val verified: Boolean
)