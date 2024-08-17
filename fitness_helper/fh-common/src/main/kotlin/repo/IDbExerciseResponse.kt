package repo

import models.FhError
import models.FhExercise


sealed interface IDbExerciseResponse: IDbResponse<FhExercise>

data class DbExerciseResponseOk(
    val data: FhExercise
): IDbExerciseResponse

data class DbExerciseResponseErr(
    val errors: List<FhError> = emptyList()
): IDbExerciseResponse {
    constructor(err: FhError): this(listOf(err))
}

data class DbExerciseResponseErrWithData(
    val exercise: FhExercise,
    val errors: List<FhError> = emptyList()
): IDbExerciseResponse {
    constructor(exercise: FhExercise, err: FhError): this(exercise, listOf(err))
}