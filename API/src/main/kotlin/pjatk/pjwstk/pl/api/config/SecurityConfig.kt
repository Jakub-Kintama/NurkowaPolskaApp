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

val frontend = "http://172.19.100.10.nip.io:3000"

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationProvider: AuthenticationProvider
) {
    // JWT Configuration
    @Bean
    fun securityFilterChain(
        http: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain = http.csrf { it.disable() }.cors { corsFilter() }.authorizeHttpRequests {
            it.requestMatchers(HttpMethod.GET, "/api/markers", "/api/markers/**").permitAll()
                .requestMatchers("/api/markers", "api/markers/**").fullyAuthenticated()
                .requestMatchers("/api/admin/markers", "/api/admin/markers/**", "/api/users", "/api/users/**")
                .hasRole("ADMIN").anyRequest().permitAll()
        }.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.NEVER)
        }.authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        .oauth2Login { it.defaultSuccessUrl(frontend) }
        .logout { it.logoutSuccessUrl(frontend) }
        .build()

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = listOf(
            "http://172.19.100.10.nip.io:3000",
            "http://172.19.100.10.nip.io:3000/",
            "http://172.19.100.10:3000",
            "http://172.19.100.10:3000/",
            "172.19.100.10.nip.io:8080",
            "172.19.100.10.nip.io:8080/",
            "172.19.100.10:8080",
            "172.19.100.10:8080/",
            "http://localhost:3000",
            "http://localhost:3000/"
        )
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        config.allowCredentials = true
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}