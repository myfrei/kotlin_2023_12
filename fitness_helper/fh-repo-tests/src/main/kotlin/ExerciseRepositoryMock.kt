import models.*
import repo.*

class ExerciseRepositoryMock(
    private val invokeCreateExercise: (DbExerciseRequest) -> IDbExerciseResponse = { DEFAULT_EXERCISE_SUCCESS_EMPTY_MOCK },
    private val invokeReadExercise: (DbExerciseIdRequest) -> IDbExerciseResponse = { DEFAULT_EXERCISE_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateExercise: (DbExerciseRequest) -> IDbExerciseResponse = { DEFAULT_EXERCISE_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteExercise: (DbExerciseIdRequest) -> IDbExerciseResponse = { DEFAULT_EXERCISE_SUCCESS_EMPTY_MOCK },
    private val invokeSearchExercise: (DbExerciseFilterRequest) -> IDbExercisesResponse = { DEFAULT_EXERCISES_SUCCESS_EMPTY_MOCK },
): IRepoExercise {
    override suspend fun createExercise(rq: DbExerciseRequest): IDbExerciseResponse {
        return invokeCreateExercise(rq)
    }

    override suspend fun readExercise(rq: DbExerciseIdRequest): IDbExerciseResponse {
        return invokeReadExercise(rq)
    }

    override suspend fun updateExercise(rq: DbExerciseRequest): IDbExerciseResponse {
        return invokeUpdateExercise(rq)
    }

    override suspend fun deleteExercise(rq: DbExerciseIdRequest): IDbExerciseResponse {
        return invokeDeleteExercise(rq)
    }

    override suspend fun searchExercise(rq: DbExerciseFilterRequest): IDbExercisesResponse {
        return invokeSearchExercise(rq)
    }

    companion object {
        val DEFAULT_EXERCISE_SUCCESS_EMPTY_MOCK = DbExerciseResponseOk(FhExercise())
        val DEFAULT_EXERCISES_SUCCESS_EMPTY_MOCK = DbExercisesResponseOk(emptyList())
    }
}
