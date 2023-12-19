package pjatk.pjwstk.pl.api.config.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties(
    val key: String? = System.getenv("JWT_SECRET"),
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
)