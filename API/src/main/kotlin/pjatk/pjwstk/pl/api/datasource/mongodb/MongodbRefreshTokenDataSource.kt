package pjatk.pjwstk.pl.api.datasource.mongodb

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.RefreshTokenDataSource

@Repository("MongodbRefreshToken")
class MongodbRefreshTokenDataSource : RefreshTokenDataSource {
    private val tokens = mutableMapOf<String, UserDetails>()

    override fun findByToken(token: String): UserDetails? =
        tokens[token]

    override fun save(token: String, userDetails: UserDetails) {
        tokens[token] = userDetails
    }
}