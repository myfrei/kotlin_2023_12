package ru.otus.fh.repo.stubs

import com.fitness.helper.stubs.FhExerciseStub
import repo.*


class ExerciseRepoStub() : IRepoExercise {
    override suspend fun createExercise(rq: DbExerciseRequest): IDbExerciseResponse {
        return DbExerciseResponseOk(
            data = FhExerciseStub.get(),
        )
    }

    override suspend fun readExercise(rq: DbExerciseIdRequest): IDbExerciseResponse {
        return DbExerciseResponseOk(
            data = FhExerciseStub.get(),
        )
    }

    override suspend fun updateExercise(rq: DbExerciseRequest): IDbExerciseResponse {
        return DbExerciseResponseOk(
            data = FhExerciseStub.get(),
        )
    }

    override suspend fun deleteExercise(rq: DbExerciseIdRequest): IDbExerciseResponse {
        return DbExerciseResponseOk(
            data = FhExerciseStub.get(),
        )
    }

    override suspend fun searchExercise(rq: DbExerciseFilterRequest): IDbExercisesResponse {
        return DbExercisesResponseOk(
            data = FhExerciseStub.prepareExerciseList(filter = ""),
        )
    }
}