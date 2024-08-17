package repo

import models.FhExercise
import models.FhExerciseId
import models.FhExerciseLock

data class DbExerciseIdRequest(
    val id: FhExerciseId,
    val lock: FhExerciseLock = FhExerciseLock.NONE,
) {
    constructor(exercise: FhExercise): this(exercise.id, exercise.lock)
}