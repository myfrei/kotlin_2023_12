package com.fitness.helper.app.spring.config

import FhCorSettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.fitness.helper.biz.FhExerciseProcessor
import config.FhAppSettings
import org.springframework.boot.context.properties.EnableConfigurationProperties
import repo.IRepoExercise
import ru.otus.fh.repo.stubs.ExerciseRepoStub
import ru.otus.otuskotlin.fh.repo.inmemory.ExerciseRepoInMemory
import ru.otus.otuskotlin.fh.repo.postgresql.RepoExerciseSql

@Suppress("unused")
@EnableConfigurationProperties(ExerciseConfigPostgres::class)
@Configuration
class ExerciseConfig(val postgresConfig: ExerciseConfigPostgres) {
    @Bean
    fun processor(corSettings: FhCorSettings) = FhExerciseProcessor(corSettings = corSettings)

    @Bean
    fun testRepo(): IRepoExercise = ExerciseRepoInMemory()

    @Bean
    fun prodRepo(): IRepoExercise = RepoExerciseSql(postgresConfig.psql)

    @Bean
    fun stubRepo(): IRepoExercise = ExerciseRepoStub()

    @Bean
    fun corSettings(): FhCorSettings = FhCorSettings(
        loggerProvider = "test",
        repoTest = testRepo(),
        repoProd = prodRepo(),
        repoStub = stubRepo(),
    )

    @Bean
    fun appSettings(
        corSettings: FhCorSettings,
        processor: FhExerciseProcessor,
    ) = FhAppSettings(
        corSettings = corSettings,
        processor = processor,
    )
}