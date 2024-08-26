package ru.otus.otuskotlin.fh.repo.postgresql

import ExerciseRepoInitialized
import IRepoExerciseInitializable
import RepoExerciseCreateTest
import RepoExerciseDeleteTest
import RepoExerciseReadTest
import RepoExerciseSearchTest
import RepoExerciseUpdateTest
import repo.IRepoExercise
import kotlin.test.AfterTest

private fun IRepoExercise.clear() {
    val pgRepo = (this as ExerciseRepoInitialized).repo as RepoExerciseSql
    pgRepo.clear()
}

class RepoExerciseSQLCreateTest : RepoExerciseCreateTest() {
    override val repo: IRepoExerciseInitializable = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { uuidNew.asString() },
    )
    @AfterTest
    fun tearDown() = repo.clear()
}

class RepoExerciseSQLReadTest : RepoExerciseReadTest() {
    override val repo: IRepoExercise = SqlTestCompanion.repoUnderTestContainer(initObjects)
    @AfterTest
    fun tearDown() = repo.clear()
}

class RepoExerciseSQLUpdateTest : RepoExerciseUpdateTest() {
    override val repo: IRepoExercise = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { lockNew.asString() },
    )
    @AfterTest
    fun tearDown() {
        repo.clear()
    }
}

class RepoExerciseSQLDeleteTest : RepoExerciseDeleteTest() {
    override val repo: IRepoExercise = SqlTestCompanion.repoUnderTestContainer(initObjects)
    @AfterTest
    fun tearDown() = repo.clear()
}

class RepoExerciseSQLSearchTest : RepoExerciseSearchTest() {
    override val repo: IRepoExercise = SqlTestCompanion.repoUnderTestContainer(initObjects)
    @AfterTest
    fun tearDown() = repo.clear()
}