package ru.otus.otuskotlin.fh.repo.inmemory

import ExerciseRepoInitialized
import RepoExerciseCreateTest
import RepoExerciseDeleteTest
import RepoExerciseReadTest
import RepoExerciseSearchTest
import RepoExerciseUpdateTest


class ExerciseRepoInMemoryCreateTest : RepoExerciseCreateTest() {
    override val repo = ExerciseRepoInitialized(
        ExerciseRepoInMemory(randomUuid = { uuidNew.asString() }),
        initObjects = initObjects,
    )
}

class ExerciseRepoInMemoryDeleteTest : RepoExerciseDeleteTest() {
    override val repo = ExerciseRepoInitialized(
        ExerciseRepoInMemory(),
        initObjects = initObjects,
    )
}

class ExerciseRepoInMemoryReadTest : RepoExerciseReadTest() {
    override val repo = ExerciseRepoInitialized(
        ExerciseRepoInMemory(),
        initObjects = initObjects,
    )
}

class ExerciseRepoInMemorySearchTest : RepoExerciseSearchTest() {
    override val repo = ExerciseRepoInitialized(
        ExerciseRepoInMemory(),
        initObjects = initObjects,
    )
}

class ExerciseRepoInMemoryUpdateTest : RepoExerciseUpdateTest() {
    override val repo = ExerciseRepoInitialized(
        ExerciseRepoInMemory(),
        initObjects = initObjects,
    )
}
