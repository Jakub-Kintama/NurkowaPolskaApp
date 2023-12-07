package pjatk.pjwstk.pl.api.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.config.jwt.JwtProperties
import pjatk.pjwstk.pl.api.controllers.auth.AuthenticationRequest
import pjatk.pjwstk.pl.api.controllers.auth.AuthenticationResponse
import pjatk.pjwstk.pl.api.datasource.RefreshTokenDataSource
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val dataSource: RefreshTokenDataSource
) {
    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email, authRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authRequest.email)

        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)

        dataSource.save(refreshToken, user)

        return AuthenticationResponse(
            accessToken = accessToken, refreshToken = refreshToken
        )
    }

    fun refreshAccessToken(token: String): String? {
        val extractedEmail = tokenService.extractEmail(token)

        return extractedEmail?.let { email ->
            val currentUserDetails = userDetailsService.loadUserByUsername(email)
            val refreshTokenUserDetails = dataSource.findByToken(token)

            if (!tokenService.isExpired(token) && currentUserDetails.username == refreshTokenUserDetails?.username) generateAccessToken(
                currentUserDetails
            )
            else null
        }
    }

    private fun generateRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user, expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )

    private fun generateAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user, expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
    )
}