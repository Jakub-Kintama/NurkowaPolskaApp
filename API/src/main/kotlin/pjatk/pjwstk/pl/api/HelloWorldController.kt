package pjatk.pjwstk.pl.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloWorldController {

    @GetMapping
    fun hello(): String{
        return "Hello"
    }

    @GetMapping("world")
    fun helloWorld(): String{
        return "World"
    }

}