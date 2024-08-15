package com.fitness.helper.app.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

// Swagger URL: http://localhost:8080/swagger-ui.html

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}