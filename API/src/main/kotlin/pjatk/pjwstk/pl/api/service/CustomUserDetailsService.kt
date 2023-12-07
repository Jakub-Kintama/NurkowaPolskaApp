package pjatk.pjwstk.pl.api.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.datasource.UserDataSource

typealias ApplicationUser = pjatk.pjwstk.pl.api.model.User

@Service
class CustomUserDetailsService(
    @Qualifier("mongodbUser") private val dataSource: UserDataSource
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        dataSource.retrieveUserByEmail(username)
            .mapToUserDetails()

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role.name)
            .build()
}