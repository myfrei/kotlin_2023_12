package com.fitness.helper.app.spring.config

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.otus.otuskotlin.fh.repo.postgresql.SqlProperties

@ConfigurationProperties(prefix = "psql")
data class ExerciseConfigPostgres(
    var host: String = "localhost",
    var port: Int = 5432,
    var user: String = "postgres",
    var password: String = "postgres",
    var database: String = "fitness_helper",
    var schema: String = "public",
    var table: String = "exercises",
) {
    val psql: SqlProperties = SqlProperties(
        host = host,
        port = port,
        user = user,
        password = password,
        database = database,
        schema = schema,
        table = table,
    )
}