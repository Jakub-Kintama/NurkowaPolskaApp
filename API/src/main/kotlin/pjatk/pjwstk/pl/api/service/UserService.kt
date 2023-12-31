package pjatk.pjwstk.pl.api.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import pjatk.pjwstk.pl.api.datasource.UserDataSource
import pjatk.pjwstk.pl.api.model.User
import pjatk.pjwstk.pl.api.model.UserEmailRole

@Service
class UserService(@Qualifier("mongodbUser") private val dataSource: UserDataSource) {
    fun getUsers(): Collection<User> = dataSource.retrieveUsers()
    fun getUserByEmail(userId: String): User = dataSource.retrieveUserByEmail(userId)
    fun addUser(user: User): User = dataSource.createUser(user)
    fun updateUser(userEmailRole: UserEmailRole): User = dataSource.updateUser(userEmailRole)
    fun deleteUser(userId: String): Unit = dataSource.deleteUser(userId)
}