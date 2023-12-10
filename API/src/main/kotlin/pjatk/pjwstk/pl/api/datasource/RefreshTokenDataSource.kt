package pjatk.pjwstk.pl.api.datasource

import org.springframework.security.core.userdetails.UserDetails

interface RefreshTokenDataSource {
    fun findByToken(token: String): UserDetails?
    fun save(token: String, userDetails: UserDetails)
}