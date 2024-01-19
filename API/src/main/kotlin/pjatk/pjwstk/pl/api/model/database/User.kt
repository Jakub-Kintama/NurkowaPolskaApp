package pjatk.pjwstk.pl.api.model.database

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import pjatk.pjwstk.pl.api.model.enums.Role

@Document("user")
data class User(
    @Id
    @JsonProperty("email")
    @Indexed(unique = true)
    val email: String,
    @JsonProperty("password")
    val password: String? = null,
    @JsonProperty("role")
    var role: Role = Role.USER
)