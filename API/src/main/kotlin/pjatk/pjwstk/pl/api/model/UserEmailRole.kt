package pjatk.pjwstk.pl.api.model

import pjatk.pjwstk.pl.api.model.enums.Role

data class UserEmailRole(
    val email: String,
    val role: Role
)
