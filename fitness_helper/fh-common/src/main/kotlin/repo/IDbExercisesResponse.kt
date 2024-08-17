package repo

import models.FhError
import models.FhExercise

sealed interface IDbExercisesResponse: IDbResponse<List<FhExercise>>

data class DbExercisesResponseOk(
    val data: List<FhExercise>
): IDbExercisesResponse

@Suppress("unused")
data class DbExercisesResponseErr(
    val errors: List<FhError> = emptyList()
): IDbExercisesResponse {
    constructor(err: FhError): this(listOf(err))
}