package pjatk.pjwstk.pl.api.datasource

import pjatk.pjwstk.pl.api.model.User
import pjatk.pjwstk.pl.api.model.UserEmailRole

interface UserDataSource {
    fun retrieveUsers(): Collection<User>
    fun retrieveUserByEmail(userId: String): User
    fun existsUser(userId: String): Boolean
    fun createUser(user: User): User
    fun updateUser(userEmailRole: UserEmailRole): User
    fun deleteUser(userEmail: String)
}