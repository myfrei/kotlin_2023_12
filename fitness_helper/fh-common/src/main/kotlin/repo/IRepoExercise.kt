package repo

interface IRepoExercise {
    suspend fun createExercise(rq: DbExerciseRequest): IDbExerciseResponse
    suspend fun readExercise(rq: DbExerciseIdRequest): IDbExerciseResponse
    suspend fun updateExercise(rq: DbExerciseRequest): IDbExerciseResponse
    suspend fun deleteExercise(rq: DbExerciseIdRequest): IDbExerciseResponse
    suspend fun searchExercise(rq: DbExerciseFilterRequest): IDbExercisesResponse
    companion object {
        val NONE = object : IRepoExercise {
            override suspend fun createExercise(rq: DbExerciseRequest): IDbExerciseResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readExercise(rq: DbExerciseIdRequest): IDbExerciseResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateExercise(rq: DbExerciseRequest): IDbExerciseResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteExercise(rq: DbExerciseIdRequest): IDbExerciseResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchExercise(rq: DbExerciseFilterRequest): IDbExercisesResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}