//package pjatk.pjwstk.pl.api.config
//
//import org.springframework.boot.context.properties.EnableConfigurationProperties
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.AuthenticationProvider
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import pjatk.pjwstk.pl.api.config.jwt.JwtProperties
//import pjatk.pjwstk.pl.api.datasource.UserDataSource
//import pjatk.pjwstk.pl.api.service.CustomUserDetailsService
//
//@Configuration
//@EnableConfigurationProperties(JwtProperties::class)
//class Configuration {
//    @Bean
//    fun userDetailsService(userDataSource: UserDataSource): UserDetailsService =
//        CustomUserDetailsService(userDataSource)
//
//    @Bean
//    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
//
//    @Bean
//    fun authenticationProvider(userDataSource: UserDataSource): AuthenticationProvider =
//        DaoAuthenticationProvider()
//            .also {
//                it.setUserDetailsService(userDetailsService(userDataSource))
//                it.setPasswordEncoder(encoder())
//            }
//
//    @Bean
//    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
//        config.authenticationManager
//}