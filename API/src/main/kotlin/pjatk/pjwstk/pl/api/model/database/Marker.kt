package pjatk.pjwstk.pl.api.model.database

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import pjatk.pjwstk.pl.api.model.enums.CrayfishType
import java.time.LocalDate

@Document("marker")
data class Marker(
        @Id
    @JsonProperty("_id")
    val id: String? = null,
        @JsonProperty("mapMarker")
    val mapMarker: MapMarker,
        @JsonProperty("userEmail")
    val userEmail: String,
        @JsonProperty("CrayfishType")
    val crayfishType: CrayfishType,
        @JsonProperty("date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    val date: LocalDate,
        @JsonProperty("verified")
    val verified: Boolean,
        @JsonProperty("image")
    val image: Image? = null
)