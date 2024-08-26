package repo.exceptions

import models.FhExerciseId

open class RepoExerciseException(
    @Suppress("unused")
    val exerciseId: FhExerciseId,
    msg: String,
): RepoException(msg)