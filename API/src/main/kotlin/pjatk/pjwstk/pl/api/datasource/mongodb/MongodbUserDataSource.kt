package pjatk.pjwstk.pl.api.datasource.mongodb

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import pjatk.pjwstk.pl.api.datasource.UserDataSource
import pjatk.pjwstk.pl.api.model.Marker
import pjatk.pjwstk.pl.api.model.User
import java.io.IOException

@Repository("mongodbUser")
class MongodbUserDataSource(
    @Autowired private val mongoTemplate: MongoTemplate
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
        return mongoTemplate.save(user)
    }

    override fun deleteUser(userId: String) {
        val query = Query.query(Criteria.where("id").`is`(userId))
        val existingUser = mongoTemplate.findOne(query, User::class.java)
            ?: throw NoSuchElementException("Could not find a user with id $userId.")

        mongoTemplate.remove(existingUser)
    }
}