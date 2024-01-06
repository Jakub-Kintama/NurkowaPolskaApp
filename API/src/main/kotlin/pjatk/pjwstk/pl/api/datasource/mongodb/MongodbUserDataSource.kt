package pjatk.pjwstk.pl.api.datasource.mongodb

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.UserDataSource
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.model.User
import pjatk.pjwstk.pl.api.model.UserEmailRole
import java.io.IOException

@Repository("mongodbUser")
class MongodbUserDataSource(
    @Autowired private val mongoTemplate: MongoTemplate,
    private val encoder: PasswordEncoder
) : UserDataSource {
    override fun retrieveUsers(): Collection<User> {
        val users = mongoTemplate.findAll(User::class.java)
        return users.ifEmpty {
            throw IOException("Could not fetch users from the database")
        }
    }

    override fun retrieveUserByEmail(userId: String): User {
        return mongoTemplate.findById(userId, User::class.java)
            ?: throw NoSuchElementException("Could not find a user with id $userId.")
    }

    override fun createUser(user: User): User {
        val userId = user.email
        if (mongoTemplate.exists(Query.query(Criteria.where("_id").`is`(userId)), Marker::class.java)) {
            throw IllegalArgumentException("User with id $userId already exists.")
        }
        
        val updatedUser = user.copy(password = encoder.encode(user.password))
        return mongoTemplate.save(updatedUser)
    }

    override fun updateUser(userEmailRole: UserEmailRole): User {
        val userEmail = userEmailRole.email
        val query = Query.query(Criteria.where("_id").`is`(userEmail))
        var user = mongoTemplate.findOne(query, User::class.java)
            ?: throw java.util.NoSuchElementException("Could not find a user with email $userEmailRole.")

        user.role = userEmailRole.role
        mongoTemplate.save(user)
        return user
    }

    override fun deleteUser(userEmail: String) {
        val query = Query.query(Criteria.where("_id").`is`(userEmail))
        val existingUser = mongoTemplate.findOne(query, User::class.java)
            ?: throw NoSuchElementException("Could not find a user with email $userEmail.")

        mongoTemplate.remove(existingUser)
    }
}