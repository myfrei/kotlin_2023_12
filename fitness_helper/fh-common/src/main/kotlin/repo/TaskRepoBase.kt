package repo

import helpers.errorSystem

abstract class ExerciseRepoBase: IRepoExercise {

    protected suspend fun tryExerciseMethod(block: suspend () -> IDbExerciseResponse) = try {
        block()
    } catch (e: Throwable) {
        DbExerciseResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryExercisesMethod(block: suspend () -> IDbExercisesResponse) = try {
        block()
    } catch (e: Throwable) {
        DbExercisesResponseErr(errorSystem("methodException", e = e))
    }
}