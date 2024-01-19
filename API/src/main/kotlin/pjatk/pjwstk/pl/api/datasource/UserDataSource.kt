package pjatk.pjwstk.pl.api.datasource

import pjatk.pjwstk.pl.api.model.database.User
import pjatk.pjwstk.pl.api.model.responses.UserResponse

interface UserDataSource {
    fun retrieveUsers(): Collection<User>
    fun retrieveUserByEmail(userId: String): User
    fun existsUser(userId: String): Boolean
    fun createUser(user: User): User
    fun updateUser(userResponse: UserResponse): User
    fun deleteUser(userEmail: String)
}