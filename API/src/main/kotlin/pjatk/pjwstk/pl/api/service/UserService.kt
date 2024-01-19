package pjatk.pjwstk.pl.api.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.datasource.UserDataSource
import pjatk.pjwstk.pl.api.model.database.User
import pjatk.pjwstk.pl.api.model.responses.UserResponse

@Service
class UserService(@Qualifier("mongodbUser") private val dataSource: UserDataSource) {
    fun getUsers(): Collection<User> = dataSource.retrieveUsers()
    fun getUserByEmail(userId: String): User = dataSource.retrieveUserByEmail(userId)
    fun addUser(user: User): User = dataSource.createUser(user)
    fun updateUser(userResponse: UserResponse): User = dataSource.updateUser(userResponse)
    fun deleteUser(userId: String): Unit = dataSource.deleteUser(userId)
}