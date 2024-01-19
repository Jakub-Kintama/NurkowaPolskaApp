package pjatk.pjwstk.pl.api.model.responses

import pjatk.pjwstk.pl.api.model.enums.Role

data class UserResponse(
    val email: String,
    val role: Role
)
