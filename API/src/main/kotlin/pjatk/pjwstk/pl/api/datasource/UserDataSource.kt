package pjatk.pjwstk.pl.api.datasource

import pjatk.pjwstk.pl.api.model.User

interface UserDataSource {
    fun retrieveUsers(): Collection<User>
    fun retrieveUserByEmail(userId: String): User
    fun createUser(user: User): User
    fun deleteUser(userId: String)
}