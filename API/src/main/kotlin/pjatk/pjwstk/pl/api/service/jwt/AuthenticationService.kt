package pjatk.pjwstk.pl.api.service.jwt


import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import pjatk.pjwstk.pl.api.config.jwt.JwtProperties
import pjatk.pjwstk.pl.api.controllers.jwt.AuthenticationRequest
import pjatk.pjwstk.pl.api.controllers.jwt.AuthenticationResponse
import pjatk.pjwstk.pl.api.datasource.RefreshTokenDataSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

@Service
class AuthenticationService(
        private val authManager: AuthenticationManager,
        private val userDetailsService: CustomUserDetailsService,
        private val tokenService: TokenService,
        private val jwtProperties: JwtProperties,
        private val dataSource: RefreshTokenDataSource
) {
    fun exchangeToken(idToken: String): AuthenticationResponse {
        if (isRs256(idToken)) {
            val email = verifyGoogleIdToken(idToken) as String
            val user = userDetailsService.loadUserByUsername(email)

            val accessToken = generateAccessToken(user)
            val refreshToken = generateRefreshToken(user)

            dataSource.save(refreshToken, user)

            return AuthenticationResponse(
                    email = user.username, role = user.authorities, accessToken = accessToken, refreshToken = refreshToken
            )
        } else {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid id token")
        }
    }

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
                email = user.username, role = user.authorities, accessToken = accessToken, refreshToken = refreshToken
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

    private fun isRs256(jwt: String): Boolean {
        val parts = jwt.split('.')
        return parts.size == 3
    }

    private fun verifyGoogleIdToken(idToken: String): String? {
        val endpoint = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=$idToken"

        try {
            val url = URL(endpoint)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = reader.readText()
                reader.close()

                val responseMap = response.split(",").associate {
                    val (key, value) = it.split(":").map { it.trim('"', ' ', '\n') }
                    key to value
                }

                val email = responseMap["email"]

                return email
            } else {
                throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Verification of Google ID token failed")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}