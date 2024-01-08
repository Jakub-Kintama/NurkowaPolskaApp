package pjatk.pjwstk.pl.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import pjatk.pjwstk.pl.api.config.jwt.JwtAuthenticationFilter
import pjatk.pjwstk.pl.api.service.oauth2.GoogleOAuth2UserService


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationProvider: AuthenticationProvider,
    private val googleOAuth2UserService: GoogleOAuth2UserService
) {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain = http.csrf {
        it.disable()
    }
        .cors {
            corsFilter()
        }
        .authorizeHttpRequests {
            it.requestMatchers(HttpMethod.GET, "/api/markers", "/api/markers/**").permitAll()
                .requestMatchers("/api/markers", "api/markers/**").fullyAuthenticated()
                .requestMatchers("/api/admin/markers", "/api/admin/markers/**", "/api/users", "/api/users/**")
                .hasRole("ADMIN").anyRequest().permitAll()
        }
        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .authenticationProvider(authenticationProvider)
        .oauth2Login { it ->
            it.userInfoEndpoint{
                it.userService(googleOAuth2UserService)
            }
        }
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java).build()

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = listOf("http://localhost:3000/*", "http://localhost:8080/*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}