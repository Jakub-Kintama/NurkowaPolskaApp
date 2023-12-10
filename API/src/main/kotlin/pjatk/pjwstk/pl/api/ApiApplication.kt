package pjatk.pjwstk.pl.api

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import pjatk.pjwstk.pl.api.model.User
import pjatk.pjwstk.pl.api.model.enums.Role
import pjatk.pjwstk.pl.api.service.UserService

@SpringBootApplication
class ApiApplication
{
    @Bean   //REMOVE
    fun init(service: UserService): ApplicationRunner {
        return ApplicationRunner { args: ApplicationArguments ->
            println("Initialized with basic users injection")

            service.addUser(User("user@gmail.com", "user", Role.USER))
            service.addUser(User("admin@gmail.com", "admin", Role.ADMIN))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}