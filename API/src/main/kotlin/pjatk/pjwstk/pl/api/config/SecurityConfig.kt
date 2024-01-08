package pjatk.pjwstk.pl.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig{

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOriginPatterns = listOf("*")
        configuration.allowedMethods = listOf("*")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.GET, "/api/markers", "/api/markers/**").permitAll()
                    .requestMatchers("/api/markers", "api/markers/**").fullyAuthenticated()
                    .requestMatchers("/api/admin/markers", "/api/admin/markers/**", "/api/users", "/api/users/**")
                    .hasRole("ADMIN").anyRequest().permitAll()
            }
            .csrf { it.disable() }
            .cors { c -> c.configurationSource(corsConfigurationSource()) }
            .sessionManagement{
                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }
            .oauth2Login { l -> l.defaultSuccessUrl("http://localhost:8080/api") }
            .build()
    }
}