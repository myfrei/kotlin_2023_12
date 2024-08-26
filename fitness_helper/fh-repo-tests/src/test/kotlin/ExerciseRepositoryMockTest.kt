package ru.otus.otuskotlin.fh.repo.tests

import ExerciseRepositoryMock
import com.fitness.helper.stubs.FhExerciseStub
import kotlinx.coroutines.test.runTest
import models.*
import repo.*
import kotlin.test.*

class ExerciseRepositoryMockTest {
    private val repo = ExerciseRepositoryMock(
        invokeCreateExercise = { DbExerciseResponseOk(FhExerciseStub.prepareResult { name = "create" }) },
        invokeReadExercise = { DbExerciseResponseOk(FhExerciseStub.prepareResult { name = "read" }) },
        invokeUpdateExercise = { DbExerciseResponseOk(FhExerciseStub.prepareResult { name = "update" }) },
        invokeDeleteExercise = { DbExerciseResponseOk(FhExerciseStub.prepareResult { name = "delete" }) },
        invokeSearchExercise = { DbExercisesResponseOk(listOf(FhExerciseStub.prepareResult { name = "search" })) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createExercise(DbExerciseRequest(FhExercise()))
        assertIs<DbExerciseResponseOk>(result)
        assertEquals("create", result.data.name)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readExercise(DbExerciseIdRequest(FhExercise()))
        assertIs<DbExerciseResponseOk>(result)
        assertEquals("read", result.data.name)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateExercise(DbExerciseRequest(FhExercise()))
        assertIs<DbExerciseResponseOk>(result)
        assertEquals("update", result.data.name)
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteExercise(DbExerciseIdRequest(FhExercise()))
        assertIs<DbExerciseResponseOk>(result)
        assertEquals("delete", result.data.name)
    }

    @Test
    fun mockSearch() = runTest {
        val result = repo.searchExercise(DbExerciseFilterRequest())
        assertIs<DbExercisesResponseOk>(result)
        assertEquals("search", result.data.first().name)
    }

}
