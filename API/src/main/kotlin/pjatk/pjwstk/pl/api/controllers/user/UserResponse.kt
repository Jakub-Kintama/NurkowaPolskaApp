package pjatk.pjwstk.pl.api.controllers.user

import pjatk.pjwstk.pl.api.model.enums.Role

data class UserResponse(
    val email: String,
    val role: Role
)
