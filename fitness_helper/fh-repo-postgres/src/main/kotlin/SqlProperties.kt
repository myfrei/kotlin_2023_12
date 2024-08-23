package ru.otus.otuskotlin.fh.repo.postgresql

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "postgres",
    val database: String = "fh_exercises",
    val schema: String = "public",
    val table: String = "exercises",
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}