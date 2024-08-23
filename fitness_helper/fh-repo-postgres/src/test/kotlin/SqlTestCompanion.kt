package ru.otus.otuskotlin.fh.repo.postgresql

import ExerciseRepoInitialized
import IRepoExerciseInitializable
import com.benasher44.uuid.uuid4
import models.FhExercise

object SqlTestCompanion {
    private const val HOST = "localhost"
    private const val USER = "postgres"
    private const val PASS = "postgres"
    val PORT = getEnv("postgresPort")?.toIntOrNull() ?: 5432

    fun repoUnderTestContainer(
        initObjects: Collection<FhExercise> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): IRepoExerciseInitializable = ExerciseRepoInitialized(
        repo = RepoExerciseSql(
            SqlProperties(
                host = HOST,
                user = USER,
                password = PASS,
                port = PORT,
            ),
            randomUuid = randomUuid
        ),
        initObjects = initObjects,
    )
}

