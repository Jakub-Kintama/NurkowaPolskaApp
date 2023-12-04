package pjatk.pjwstk.pl.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("admin")
data class Admin(
    @Id
    @JsonProperty("id")
    val id: String
)